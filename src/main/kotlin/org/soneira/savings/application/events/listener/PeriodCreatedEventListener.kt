package org.soneira.savings.application.events.listener

import org.soneira.savings.domain.event.PeriodCreatedEvent
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class PeriodCreatedEventListener(val fileRepository: FileRepository) {
    @EventListener
    @Async
    fun listen(periodsCreatedEvent: PeriodCreatedEvent) {
        //TODO: move to a new application service within a transaction
        fileRepository.remove(periodsCreatedEvent.file)
    }
}