package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.event.MovementUpdatedEvent
import org.soneira.savings.domain.model.vo.EditableMovement
import org.soneira.savings.domain.repository.MovementRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.EditMovementUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EditMovementUseCaseImpl(
    private val movementRepository: MovementRepository,
    private val userRepository: UserRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
) : EditMovementUseCase {

    @Transactional
    override fun edit(movement: EditableMovement) {
        val user = userRepository.getUser("john.doe@gmail.com")
        val movementUpdatedEvent = MovementUpdatedEvent(user, movementRepository.edit(user, movement))
        applicationEventPublisher.publishEvent(movementUpdatedEvent)
    }
}