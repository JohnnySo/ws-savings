package org.soneira.savings

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SavingsApplication

fun main(args: Array<String>) {
    runApplication<SavingsApplication>(*args)
}
