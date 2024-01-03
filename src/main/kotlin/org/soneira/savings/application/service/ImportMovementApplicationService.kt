package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.soneira.savings.domain.port.output.filereader.FileReader
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.service.period.PeriodCreator
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class ImportMovementApplicationService (
    val periodCreator: PeriodCreator,
    val userRepository: UserRepository,
    val periodRepository: PeriodRepository,
    val fileRepository: FileRepository,
    val reader: FileReader,
    val applicationEventPublisher: ApplicationEventPublisher
): ImportMovementApplicationService {

    override fun preview(file: InputStream, filename: String): File {
        val user = userRepository.getUser("test")
        return fileRepository.save(user, filename, reader.read(filename, file, user.settings.fileParserSettings))
    }

    override fun import(fileId: String): List<EconomicPeriod> {
        val user = userRepository.getUser("test")
        val periodsCreatedEvent = periodCreator.create(user, fileRepository.get(fileId), periodRepository.findLastPeriod())
        applicationEventPublisher.publishEvent(periodsCreatedEvent)
        return periodsCreatedEvent.economicPeriods
    }
}