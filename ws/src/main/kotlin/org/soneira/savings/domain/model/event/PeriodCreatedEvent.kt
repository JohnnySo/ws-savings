package org.soneira.savings.domain.model.event

import org.soneira.savings.domain.model.entity.EconomicPeriod
import org.soneira.savings.domain.model.entity.File

data class PeriodCreatedEvent(val file: File, val economicPeriods: List<EconomicPeriod>)