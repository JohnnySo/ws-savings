package org.soneira.savings.application.service

import org.soneira.savings.domain.event.MovementUpdatedEvent
import org.soneira.savings.domain.port.input.EditMovementApplicationService
import org.soneira.savings.domain.port.output.repository.MovementRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.vo.EditableMovement
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EditMovementApplicationServiceImpl(
    private val movementRepository: MovementRepository,
    private val userRepository: UserRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
) : EditMovementApplicationService {

    @Transactional
    override fun edit(movement: EditableMovement) {
        val user = userRepository.getUser("john.doe@gmail.com")
        val movementUpdatedEvent = MovementUpdatedEvent(user, movementRepository.edit(user, movement))
        applicationEventPublisher.publishEvent(movementUpdatedEvent)
    }
}