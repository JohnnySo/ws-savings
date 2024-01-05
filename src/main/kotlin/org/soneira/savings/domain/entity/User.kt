package org.soneira.savings.domain.entity

import org.soneira.savings.domain.vo.Settings
import org.soneira.savings.domain.vo.id.UserId

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val settings: Settings,
) {
    var id: UserId = UserId("")

    constructor(id: UserId, name: String, surname: String, email: String, settings: Settings) :
            this(name, surname, email, settings) {
        this.id = id
    }
}