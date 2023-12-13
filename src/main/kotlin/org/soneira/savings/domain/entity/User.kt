package org.soneira.savings.domain.entity

import org.soneira.savings.domain.common.entity.BaseEntity
import org.soneira.savings.domain.vo.BusinessSettings
import org.soneira.savings.domain.vo.id.UserId

data class User(
    val name: String,
    val surname: String,
    val email: String,
    val settings: BusinessSettings,
) : BaseEntity<UserId>()