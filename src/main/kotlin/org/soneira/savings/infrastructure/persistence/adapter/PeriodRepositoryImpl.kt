package org.soneira.savings.infrastructure.persistence.adapter

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.infrastructure.persistence.mongo.mapper.PeriodMapper
import org.soneira.savings.infrastructure.persistence.mongo.repository.MovementMongoRepository
import org.soneira.savings.infrastructure.persistence.mongo.repository.PeriodMongoRepository
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
}