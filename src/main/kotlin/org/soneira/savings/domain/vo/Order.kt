package org.soneira.savings.domain.vo

data class Order(var value: Int) : Comparable<Order> {
    override fun compareTo(other: Order): Int = value.compareTo(other.value)
}