package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.BaseEntity
import org.soneira.savings.domain.vo.id.FileId

data class File(val user: User, val filename: String, val movements: List<Movement>): BaseEntity<FileId>() {
}