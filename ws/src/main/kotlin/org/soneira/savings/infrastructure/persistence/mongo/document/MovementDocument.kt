package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.TextIndexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.mapping.FieldType.DECIMAL128
import org.springframework.data.mongodb.core.mapping.FieldType.INT32
import java.math.BigDecimal
import java.time.LocalDate

@Document("movements")
data class MovementDocument(
    val operationDate: LocalDate,

    @TextIndexed var description: String,
    @Field(targetType = DECIMAL128) val amount: BigDecimal,
    var order: Int,

    @Field(targetType = INT32)
    var subcategory: Int,

    @TextIndexed var comment: String,
    @Field(targetType = DECIMAL128)
    val balance: BigDecimal,
    val user: String
) {
    @Id
    lateinit var id: String
    lateinit var period: String

    constructor(
        id: String, operationDate: LocalDate, description: String, amount: BigDecimal, order: Int,
        subcategory: Int, comment: String, balance: BigDecimal, user: String
    ) :
            this(operationDate, description, amount, order, subcategory, comment, balance, user) {
        this.id = id
    }

    /**
     * Check if the lateinit prop id is initialized
     * @return true if it is initialized
     */
    fun isIdInit(): Boolean {
        return ::id.isInitialized
    }
}