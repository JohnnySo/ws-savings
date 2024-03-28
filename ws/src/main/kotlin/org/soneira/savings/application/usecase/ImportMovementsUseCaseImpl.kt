package org.soneira.savings.application.usecase

import org.soneira.savings.application.usecase.reader.ReaderFactory
import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.vo.id.FileId
import org.soneira.savings.domain.repository.FileRepository
import org.soneira.savings.domain.repository.PeriodRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.service.PeriodCreator
import org.soneira.savings.domain.usecase.ImportMovementsUseCase
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class ImportMovementsUseCaseImpl(
    val periodCreator: PeriodCreator,
    val userRepository: UserRepository,
    val periodRepository: PeriodRepository,
    val fileRepository: FileRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
    val readerFactory: ReaderFactory,
) : ImportMovementsUseCase {

    override fun preview(file: InputStream, filename: String): File {
        val user = userRepository.getUser("john.doe@gmail.com")
        val movements = readerFactory.getReader(user.settings.fileParserSettings.fileType)
            .read(file, user.settings.fileParserSettings)
        return fileRepository.save(user, filename, movements)
    }

    override fun import(id: FileId) {
        val user = userRepository.getUser("john.doe@gmail.com")
        val periodsCreatedEvent =
            periodCreator.create(user, fileRepository.get(id, user), periodRepository.findLastPeriod(user))
        periodRepository.save(periodsCreatedEvent.economicPeriods)
        applicationEventPublisher.publishEvent(periodsCreatedEvent)
    }
}