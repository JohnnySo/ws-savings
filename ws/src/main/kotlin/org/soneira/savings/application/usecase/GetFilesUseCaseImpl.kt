package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.entity.File
import org.soneira.savings.domain.model.vo.id.FileId
import org.soneira.savings.domain.repository.FileRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.GetFilesUseCase
import org.springframework.stereotype.Service

@Service
class GetFilesUseCaseImpl(private val fileRepository: FileRepository,
                          private val userRepository: UserRepository): GetFilesUseCase {
    override fun getFiles(): List<File> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return fileRepository.getAll(user)
    }

    override fun getFile(id: FileId): File {
        val user = userRepository.getUser("john.doe@gmail.com")
        return fileRepository.get(id, user)
    }
}