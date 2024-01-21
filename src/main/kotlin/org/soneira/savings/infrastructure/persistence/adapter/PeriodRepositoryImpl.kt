package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.domain.vo.SortDirection
import org.soneira.savings.domain.vo.id.PeriodId
import org.soneira.savings.domain.vo.id.UserId
import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.soneira.savings.infrastructure.persistence.mongo.mapper.PeriodMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.MovementMongoRepository
import org.soneira.savings.infrastructure.persistence.mongo.repository.PeriodMongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.domain.Sort.Order
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Component
@Transactional
class PeriodRepositoryImpl(
    private val periodMapper: PeriodMapper,
    private val periodMongoRepository: PeriodMongoRepository,
    private val movementMongoRepository: MovementMongoRepository
) : PeriodRepository {
    @Transactional(readOnly = true)
    override fun findLastPeriod(user: User): Optional<EconomicPeriod> {
        val periods = periodMongoRepository.findLastPeriod(user.id.value)
        return if (periods.isEmpty()) {
            Optional.empty()
        } else {
            val lastPeriod = periods.first()
            lastPeriod.movements = movementMongoRepository.findByPeriodId(lastPeriod.id)
            Optional.of(periodMapper.toDomain(lastPeriod))
        }
    }

    override fun save(economicPeriods: List<EconomicPeriod>): List<EconomicPeriod> {
        val periodDocuments = periodMongoRepository.saveAll(economicPeriods.map { periodMapper.toDocument(it) })
        periodDocuments.forEach { period -> period.movements.forEach { it.periodId = period.id } }
        val movementDocuments = movementMongoRepository.saveAll(periodDocuments.flatMap { it.movements })
        periodDocuments.forEach { period ->
            period.movements = movementDocuments.filter { it.periodId == period.id }
        }
        return periodDocuments.map { periodMapper.toDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun getPeriods(
        userId: UserId,
        limit: Int,
        offset: Int,
        sortBy: String,
        sortDirection: SortDirection
    ): Page<EconomicPeriod> {
        val pageNumber = (offset / limit)
        val sort = Sort.by(Order(Direction.fromString(sortDirection.value), sortBy))
        val pageable: Pageable = PageRequest.of(pageNumber, limit, sort)
        val page: Page<EconomicPeriodDocument> = periodMongoRepository.findAllByUser(userId.value, pageable)
        return periodMapper.toDomain(page)
    }

    @Transactional(readOnly = true)
    override fun getPeriod(userId: UserId, id: PeriodId): EconomicPeriod {
        val optPeriodDocument = periodMongoRepository.findByUserAndId(userId.value, id.value)
        if (!optPeriodDocument.isPresent) {
            throw ResourceNotFoundException("The period ${id.value} for user ${userId.value} do not exist.")
        } else {
            val periodDocument = optPeriodDocument.get()
            periodDocument.movements = movementMongoRepository.findByUserAndPeriodId(userId.value, id.value)
            return periodMapper.toDomain(periodDocument)
        }
    }
}