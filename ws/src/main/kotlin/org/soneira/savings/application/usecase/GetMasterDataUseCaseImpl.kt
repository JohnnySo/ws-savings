package org.soneira.savings.application.usecase

import org.soneira.savings.domain.repository.MasterRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.GetMasterDataUseCase
import org.springframework.stereotype.Service

@Service
class GetMasterDataUseCaseImpl(
    val masterRepository: MasterRepository,
    val userRepository: UserRepository
) : GetMasterDataUseCase {
    override fun getYears(): List<Int> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return masterRepository.getYears(user)
    }
}