package org.soneira.savings.domain.vo.params

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.Saving
import org.soneira.savings.domain.entity.User
import java.util.*

class SavingsCreatorParams(
    val user: User,
    val filename: String,
    val movements: List<Movement>,
    val optLastSaving: Optional<Saving>
) {
}