package org.soneira.savings.domain.port.input

import org.soneira.savings.domain.entity.Movement
import org.soneira.savings.domain.entity.EconomicPeriod

interface ImportMovementApplicationService {
    fun execute(movements: List<Movement>, filename: String): List<EconomicPeriod>
}