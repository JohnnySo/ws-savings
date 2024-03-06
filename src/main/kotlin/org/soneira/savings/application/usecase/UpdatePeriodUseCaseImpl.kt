package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.repository.PeriodRepository
import org.soneira.savings.domain.usecase.UpdatePeriodUseCase
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdatePeriodUseCaseImpl(private val periodRepository: PeriodRepository) :
    UpdatePeriodUseCase {
    override fun update(user: User, movement: Movement) {
        val period = periodRepository.getPeriodOfMovement(user, movement.id)
        period.ifPresent {
            it.init()
            periodRepository.save(listOf(it))
        }
    }
}