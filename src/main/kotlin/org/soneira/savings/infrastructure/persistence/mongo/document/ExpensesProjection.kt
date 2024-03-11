package org.soneira.savings.infrastructure.persistence.mongo.document

import org.springframework.data.annotation.Id

class ExpensesProjection(@Id val year: Int, val expenses: List<ExpenseProjection>)