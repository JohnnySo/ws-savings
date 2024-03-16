package org.soneira.savings.infrastructure.repository

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.model.exception.ResourceNotFoundException
import org.soneira.savings.domain.model.vo.SortDirection
import org.soneira.savings.domain.model.vo.id.MovementId
import org.soneira.savings.domain.model.vo.id.PeriodId
import org.soneira.savings.domain.repository.PeriodRepository
import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.soneira.savings.infrastructure.persistence.mongo.mapper.PeriodMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.MovementMongoRepository
import org.soneira.savings.infrastructure.persistence.mongo.repository.PeriodMongoRepository
import org.springframework.cache.annotation.CacheEvict
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
class PeriodRepositoryImpl(
    private val periodMapper: PeriodMapper,
    private val periodMongoRepository: PeriodMongoRepository,
    private val movementMongoRepository: MovementMongoRepository
) : PeriodRepository {

    override fun findLastPeriod(user: User): Optional<EconomicPeriod> {
        val periods = periodMongoRepository.findLastPeriod(user.id.value)
        return if (periods.isEmpty()) {
            Optional.empty()
        } else {
            val lastPeriod = periods.first()
            lastPeriod.movements = movementMongoRepository.findByPeriodId(lastPeriod.id)
            Optional.of(periodMapper.asPeriod(lastPeriod))
        }
    }

    @Transactional
    @CacheEvict("years", allEntries = true)
    override fun save(economicPeriods: List<EconomicPeriod>): List<EconomicPeriod> {
        val periodDocuments = periodMongoRepository.saveAll(economicPeriods.map { periodMapper.asPeriodDocument(it) })
        periodDocuments.forEach { period -> period.movements.forEach { it.periodId = period.id } }
        val movementDocuments = movementMongoRepository.saveAll(periodDocuments.flatMap { it.movements })
        periodDocuments.forEach { period ->
            period.movements = movementDocuments.filter { it.periodId == period.id }
        }
        return periodDocuments.map { periodMapper.asPeriod(it) }
    }

    override fun getPeriods(
        user: User,
        limit: Int,
        offset: Int,
        sortBy: String,
        sortDirection: SortDirection
    ): Page<EconomicPeriod> {
        val pageNumber = (offset / limit)
        val sort = Sort.by(Order(Direction.fromString(sortDirection.value), sortBy))
        val pageable: Pageable = PageRequest.of(pageNumber, limit, sort)
        val page: Page<EconomicPeriodDocument> = periodMongoRepository.findAllByUser(user.id.value, pageable)
        return periodMapper.asPageOfPeriods(page)
    }

    override fun getPeriod(user: User, id: PeriodId): EconomicPeriod {
        val optPeriodDocument = periodMongoRepository.findByUserAndId(user.id.value, id.value)
        if (!optPeriodDocument.isPresent) {
            throw ResourceNotFoundException("The period ${id.value} for user ${user.id.value} do not exist.")
        } else {
            val periodDocument = optPeriodDocument.get()
            periodDocument.movements = movementMongoRepository.findByUserAndPeriodId(user.id.value, id.value)
            return periodMapper.asPeriod(periodDocument)
        }
    }

    override fun getPeriodOfMovement(user: User, movement: MovementId): Optional<EconomicPeriod> {
        val movementDocument = movementMongoRepository.findByUserAndId(user.id.value, movement.value)
        return if (movementDocument.isPresent) {
            Optional.of(this.getPeriod(user, PeriodId(movementDocument.get().periodId)))
        } else {
            Optional.empty()
        }
    }
}