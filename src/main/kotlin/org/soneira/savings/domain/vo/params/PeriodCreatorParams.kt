package org.soneira.savings.domain.vo.params

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.User
import java.util.*

class PeriodCreatorParams(
    val user: User,
    val filename: String,
    val movements: List<Movement>,
    val optLastPeriod: Optional<EconomicPeriod>
) {
}