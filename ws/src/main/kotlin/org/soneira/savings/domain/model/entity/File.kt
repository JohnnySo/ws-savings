package org.soneira.savings.domain.model.entity

import org.soneira.savings.domain.model.vo.id.FileId

data class File(val user: User, val filename: String, val movements: List<Movement>) {
    lateinit var id: FileId

    constructor(id: FileId, user: User, filename: String, movements: List<Movement>) : this(user, filename, movements) {
        this.id = id
    }
}