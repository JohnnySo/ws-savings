package org.soneira.savings.domain.event

import org.soneira.savings.domain.entity.EconomicPeriod

data class PeriodCreatedEvent (val economicPeriods: List<EconomicPeriod>){
}