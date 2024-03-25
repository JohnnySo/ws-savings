package org.soneira.savings.infrastructure.persistence.mongo.repository

import com.mongodb.BasicDBObject
import org.soneira.savings.infrastructure.persistence.mongo.document.EconomicPeriodDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.ExpensesProjection
import org.soneira.savings.infrastructure.persistence.mongo.document.MovementDocument
import org.soneira.savings.infrastructure.persistence.mongo.document.TotalsProjection
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.domain.Sort.Direction.DESC
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.springframework.data.mongodb.core.aggregation.Fields
import org.springframework.data.mongodb.core.aggregation.MatchOperation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class CustomMongoRepositoryImpl(val mongodbTemplate: MongoTemplate) : CustomMongoRepository {

    override fun searchMovements(user: String, searchParam: String): List<MovementDocument> {
        val criteria = Criteria.where("user").`is`(user)
            .orOperator(
                Criteria.where("description").regex(".*$searchParam.*", "i"),
                Criteria.where("comment").regex(".*$searchParam.*", "i")
            )
        val query = Query.query(criteria).with(Sort.by(Sort.Order.by("description")))
        return mongodbTemplate.find(query, MovementDocument::class.java)
    }

    override fun getTotalsByYear(user: String, years: List<Int>): List<TotalsProjection> {
        val groupStage = Aggregation.group("year")
            .sum("totals.saved").`as`("totalSaved")
            .sum("totals.income").`as`("totalIncome")
            .sum("totals.expense").`as`("totalExpense")
        val sortStage = Aggregation.sort(Sort.by("_id").descending())
        val aggregation = Aggregation.newAggregation(getMatchStage(user, years), groupStage, sortStage)
        val result: AggregationResults<TotalsProjection> = mongodbTemplate
            .aggregate(aggregation, PERIODS_COLLECTION_NAME, TotalsProjection::class.java)
        return result.mappedResults
    }

    override fun getExpensesByYear(user: String, years: List<Int>, useSubcategory: Boolean): List<ExpensesProjection> {
        val categoryKey = if (useSubcategory) "\$expenseBySubcategory" else "\$expenseByCategory"
        val aggregation = Aggregation.newAggregation(
            getMatchStage(user, years),
            Aggregation.unwind(categoryKey),
            Aggregation.group(
                Fields.from(
                    Fields.field("year", "\$year"),
                    Fields.field("key", "$categoryKey.key")
                )
            ).sum("$categoryKey.value").`as`("spentByCategory"),
            Aggregation.group("\$_id.year").addToSet(
                BasicDBObject("key", "\$_id.key")
                    .append("amount", "\$spentByCategory")
            ).`as`("categories"),
            Aggregation.project().and("_id").`as`("_id").and("categories").`as`("categories"),
            Aggregation.unwind("\$categories"),
            Aggregation.sort(DESC, "categories.amount"),
            Aggregation.group("_id").push("categories").`as`("expenses"),
            Aggregation.sort(ASC, "_id")
        )

        val result: AggregationResults<ExpensesProjection> = mongodbTemplate
            .aggregate(aggregation, PERIODS_COLLECTION_NAME, ExpensesProjection::class.java)
        return result.mappedResults
    }

    override fun findDistinctYears(user: String): List<Int> {
        val criteria = Criteria.where("user").`is`(user)
        val query = Query.query(criteria)
        val years = mongodbTemplate.find(query, EconomicPeriodDocument::class.java)
            .distinctBy { it.year }.map { it.year }.reversed()
        return years
    }

    val getMatchStage: (String, List<Int>) -> MatchOperation = { user, years ->
        val criteria = Criteria("user").`is`(user).andOperator(Criteria("year").`in`(years))
        Aggregation.match(criteria)
    }

    companion object {
        const val PERIODS_COLLECTION_NAME = "periods"
    }
}