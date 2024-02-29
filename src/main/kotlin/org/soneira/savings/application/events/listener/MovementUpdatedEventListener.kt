package org.soneira.savings.application.events.listener

import org.soneira.savings.domain.event.MovementUpdatedEvent
import org.soneira.savings.domain.port.input.UpdatePeriodApplicationService
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase.BEFORE_COMMIT
import org.springframework.transaction.event.TransactionalEventListener

@Component
class MovementUpdatedEventListener(val updatePeriodApplicationService: UpdatePeriodApplicationService) {
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    fun listen(movementUpdatedEvent: MovementUpdatedEvent) {
        updatePeriodApplicationService.update(movementUpdatedEvent.user, movementUpdatedEvent.movement)
    }
}