package org.soneira.savings.application.events.listener

import org.soneira.savings.domain.model.event.MovementUpdatedEvent
import org.soneira.savings.domain.usecase.UpdatePeriodUseCase
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MovementUpdatedEventListener(val updatePeriodUseCase: UpdatePeriodUseCase) {
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    fun listen(movementUpdatedEvent: MovementUpdatedEvent) {
        updatePeriodUseCase.update(movementUpdatedEvent.user, movementUpdatedEvent.movement)
    }
}