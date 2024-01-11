package org.soneira.savings.application.service

import org.soneira.savings.application.service.reader.ReaderFactory
import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.service.period.PeriodCreator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class ImportMovementApplicationServiceImpl(
    val periodCreator: PeriodCreator,
    val userRepository: UserRepository,
    val periodRepository: PeriodRepository,
    val fileRepository: FileRepository,
    val applicationEventPublisher: ApplicationEventPublisher,
    val readerFactory: ReaderFactory,
) : ImportMovementApplicationService {

    override fun preview(file: InputStream, filename: String): File {
        val user = userRepository.getUser("john.doe@gmail.com")
        val movements = readerFactory.getReader(user.settings.fileParserSettings.fileType)
            .read(file, user.settings.fileParserSettings)
        return fileRepository.save(user, filename, movements)
    }

    override fun import(fileId: String): List<EconomicPeriod> {
        val user = userRepository.getUser("john.doe@gmail.com")
        val periodsCreatedEvent =
            periodCreator.create(user, fileRepository.get(fileId, user), periodRepository.findLastPeriod(user))
        applicationEventPublisher.publishEvent(periodsCreatedEvent)
        periodRepository.save(periodsCreatedEvent.economicPeriods)
        return periodsCreatedEvent.economicPeriods
    }
}