package org.soneira.savings.application.service

import org.soneira.savings.application.service.reader.ExcelFileReaderApplicationService
import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.port.input.FileReaderApplicationService
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.soneira.savings.domain.port.output.repository.PeriodRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.service.period.PeriodCreator
import org.soneira.savings.domain.vo.FileReaderType
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.io.InputStream

@Service
class ImportMovementApplicationServiceImpl (
    val periodCreator: PeriodCreator,
    val userRepository: UserRepository,
    val periodRepository: PeriodRepository,
    val fileRepository: FileRepository,
    val applicationEventPublisher: ApplicationEventPublisher
): ImportMovementApplicationService {

    override fun preview(file: InputStream, filename: String): File {
        val user = userRepository.getUser("test")
        val preMovements = getReader(user.settings.fileParserSettings.fileType).read(file, user.settings.fileParserSettings)
        return fileRepository.save(user, filename, preMovements)
    }

    override fun import(fileId: String): List<EconomicPeriod> {
        val user = userRepository.getUser("test")
        val periodsCreatedEvent = periodCreator.create(user, fileRepository.get(fileId), periodRepository.findLastPeriod())
        applicationEventPublisher.publishEvent(periodsCreatedEvent)
        return periodsCreatedEvent.economicPeriods
    }

    /**
     * Returns a reader based on the configuration stored for this user
     * @param fileType the type of reader to instance
     * @return an instance of the required reader of type [FileReaderApplicationService]
     */
    private fun getReader(fileType: FileReaderType): FileReaderApplicationService {
        when(fileType) {
            FileReaderType.EXCEL -> return ExcelFileReaderApplicationService()
        }
    }
}