package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.File
import org.soneira.savings.domain.port.input.RemoveFileApplicationService
import org.soneira.savings.domain.port.output.repository.FileRepository
import org.springframework.stereotype.Service


@Service
class RemoveFileApplicationServiceImpl(private val fileRepository: FileRepository) : RemoveFileApplicationService {
    override fun remove(file: File) {
        fileRepository.remove(file)
    }

}