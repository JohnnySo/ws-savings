package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.port.input.UpdatePeriodApplicationService
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UpdatePeriodApplicationServiceImpl(private val periodRepository: PeriodRepository) :
    UpdatePeriodApplicationService {
    override fun update(user: User, movement: Movement) {
        val period = periodRepository.getPeriodOfMovement(user, movement.id)
        period.ifPresent { periodRepository.save(listOf(it.copy())).first() }
    }
}