package org.soneira.savings.domain.model.vo

import java.util.*

enum class SortDirection(val value: String) {
    ASC("ASC"),
    DESC("DESC");

    companion object {
        fun fromString(value: String): SortDirection {
            try {
                return SortDirection.valueOf(value.uppercase(Locale.getDefault()))
            } catch (e: Exception) {
                throw IllegalArgumentException(
                    "Invalid value $value for orders given; Has to be either 'desc' or 'asc' (case insensitive)", e
                )
            }
        }
    }
}