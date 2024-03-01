package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.repository.FileRepository
import org.soneira.savings.domain.usecase.RemoveFileUseCase
import org.springframework.stereotype.Service


@Service
class RemoveFileUseCaseImpl(private val fileRepository: FileRepository) : RemoveFileUseCase {
    override fun remove(file: File) {
        fileRepository.remove(file)
    }

}