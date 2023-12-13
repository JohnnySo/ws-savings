package org.soneira.savings.domain.event

import org.soneira.savings.domain.entity.Saving

data class SavingsCreatedEvent (val savings: List<Saving>){
}