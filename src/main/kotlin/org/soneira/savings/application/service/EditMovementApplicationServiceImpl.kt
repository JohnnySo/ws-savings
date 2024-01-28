package org.soneira.savings.application.service

import org.soneira.savings.domain.port.input.EditMovementApplicationService
import org.soneira.savings.domain.port.output.repository.MovementRepository
import org.soneira.savings.domain.port.output.repository.UserRepository
import org.soneira.savings.domain.vo.EditableMovement
import org.springframework.stereotype.Service

@Service
class EditMovementApplicationServiceImpl(
    private val movementRepository: MovementRepository,
    private val userRepository: UserRepository,
) :
    EditMovementApplicationService {
    override fun edit(movement: EditableMovement) {
        //TODO: throw event to recalculate period?
        val user = userRepository.getUser("john.doe@gmail.com")
        movementRepository.edit(user, movement)
    }
}