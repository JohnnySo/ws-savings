package org.soneira.savings.domain.model.event

import org.soneira.savings.domain.model.entity.Movement
import org.soneira.savings.domain.model.entity.User

data class MovementUpdatedEvent(val user: User, val movement: Movement)