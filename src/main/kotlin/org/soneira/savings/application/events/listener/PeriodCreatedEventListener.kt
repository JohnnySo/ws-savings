package org.soneira.savings.application.events.listener

import org.soneira.savings.application.usecase.RemoveFileUseCaseImpl
import org.soneira.savings.domain.model.event.PeriodCreatedEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PeriodCreatedEventListener(val deleteFileApplicationService: RemoveFileUseCaseImpl) {
    @EventListener
    @Async
    fun listen(periodsCreatedEvent: PeriodCreatedEvent) {
        deleteFileApplicationService.remove(periodsCreatedEvent.file)
    }
}