package org.soneira.savings.api.dto

data class FileDTO(val fileId: String, val movements: List<MovementDTO>) {
}