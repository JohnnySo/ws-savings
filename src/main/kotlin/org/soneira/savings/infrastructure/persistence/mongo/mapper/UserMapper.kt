package org.soneira.savings.infrastructure.persistence.mongo.mapper

import org.soneira.savings.domain.entity.User
import org.soneira.savings.domain.port.output.repository.SubcategoryRepository
import org.soneira.savings.domain.vo.FileParserSettings
import org.soneira.savings.domain.vo.Settings
import org.soneira.savings.domain.vo.id.UserId
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