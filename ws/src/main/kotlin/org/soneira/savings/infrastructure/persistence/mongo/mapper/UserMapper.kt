package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.model.entity.User
import org.soneira.savings.domain.repository.SubcategoryRepository
import org.soneira.savings.domain.model.vo.FileParserSettings
import org.soneira.savings.domain.model.vo.Settings
import org.soneira.savings.domain.model.vo.id.UserId
import org.soneira.savings.infrastructure.persistence.mongo.document.UserDocument
import org.springframework.stereotype.Component

@Component
class UserMapper(private val subcategoryRepository: SubcategoryRepository) {
    fun toDomain(userDocument: UserDocument): User {
        val subcategories = subcategoryRepository.getAll()
        val fileParserSettings = FileParserSettings(
            userDocument.settings.fileParserSettings.fileType,
            userDocument.settings.fileParserSettings.headerOffset,
            userDocument.settings.fileParserSettings.fieldPosition
        )
        val settings = Settings(
            userDocument.settings.periodStrategyType,
            subcategories.first { userDocument.settings.periodDefiner == it.id.value },
            userDocument.settings.monthStartBoundary,
            userDocument.settings.monthEndBoundary,
            subcategories.filter { it.id.value in userDocument.settings.subcategoriesNotCountable },
            fileParserSettings
        )
        return User(UserId(userDocument.id), userDocument.name, userDocument.surname, userDocument.email, settings)
    }
}