package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.PeriodDTO
import org.soneira.savings.api.dto.PeriodDetailDTO
import org.soneira.savings.api.mapper.PeriodApiMapper
import org.soneira.savings.domain.model.vo.SortDirection
import org.soneira.savings.domain.model.vo.id.PeriodId
import org.soneira.savings.domain.usecase.GetPeriodUseCase
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PeriodController(
    val getPeriodUseCase: GetPeriodUseCase,
    val periodApiMapper: PeriodApiMapper
) {
    @GetMapping("/periods")
    fun getPeriods(
        @RequestParam("limit") limit: Int,
        @RequestParam("offset") offset: Int,
        @RequestParam("sort-by", defaultValue = "start") sortBy: String,
        @RequestParam("direction", defaultValue = "desc") sortDirection: SortDirection
    ): ResponseEntity<Page<PeriodDTO>> {
        //FIXME: the results of expensesByCategory and subcategory are not correctly formed
        val paginatedPeriods = getPeriodUseCase.getPaginatedPeriods(limit, offset, sortBy, sortDirection)
        return if (paginatedPeriods.isEmpty) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok(periodApiMapper.asPageOfPeriodDto(paginatedPeriods))
        }
    }

    @GetMapping("/period/{id}")
    fun getPeriod(@PathVariable("id") id: String): ResponseEntity<PeriodDetailDTO> {
        val period = getPeriodUseCase.getPeriodById(PeriodId(id))
        return ResponseEntity.ok(periodApiMapper.asPeriodDetailDto(period))
    }
}