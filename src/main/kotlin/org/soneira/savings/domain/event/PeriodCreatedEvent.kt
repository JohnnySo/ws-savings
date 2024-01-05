package org.soneira.savings.domain.event

import org.soneira.savings.domain.entity.EconomicPeriod
import org.soneira.savings.domain.entity.File

data class PeriodCreatedEvent(val file: File, val economicPeriods: List<EconomicPeriod>)