package org.soneira.savings.application.usecase

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.repository.MovementRepository
import org.soneira.savings.domain.repository.UserRepository
import org.soneira.savings.domain.usecase.FindMovementUseCase
import org.springframework.stereotype.Service

@Service
class FindMovementUseCaseImpl(
    private val movementRepository: MovementRepository,
    private val userRepository: UserRepository,
) :
    FindMovementUseCase {
    override fun find(searchParam: String): List<Movement> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return movementRepository.find(user, searchParam)
    }
}