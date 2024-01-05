package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.id.FileId

data class File(val user: User, val filename: String, val movements: List<Movement>) {
    var id: FileId = FileId("")

    constructor(id: FileId, user: User, filename: String, movements: List<Movement>) : this(user, filename, movements) {
        this.id = id
    }
}