package org.soneira.savings.application.service

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.port.input.FindMovementApplicationService
import org.soneira.savings.domain.port.output.repository.MovementRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class FindMovementApplicationServiceImpl(
    private val movementRepository: MovementRepository,
    private val userRepository: UserRepository,
) :
    FindMovementApplicationService {
    override fun find(searchParam: String): List<Movement> {
        val user = userRepository.getUser("john.doe@gmail.com")
        return movementRepository.find(user.id, searchParam)
    }
}