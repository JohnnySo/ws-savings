package org.soneira.savings.domain.event

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.User

data class MovementUpdatedEvent(val user: User, val movement: Movement)