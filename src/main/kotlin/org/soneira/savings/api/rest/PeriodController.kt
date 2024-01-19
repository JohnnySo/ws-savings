package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.PeriodDTO
import org.soneira.savings.api.dto.PeriodDetailDTO
import org.soneira.savings.api.mapper.PeriodApiMapper
import org.soneira.savings.domain.port.input.GetPeriodApplicationService
import org.soneira.savings.domain.vo.SortDirection
import org.soneira.savings.domain.vo.id.PeriodId
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PeriodController(
    val getPeriodApplicationService: GetPeriodApplicationService,
    val periodApiMapper: PeriodApiMapper
) {
    @GetMapping("/periods")
    fun getPeriods(
        @RequestParam("limit") limit: Int,
        @RequestParam("offset") offset: Int,
        @RequestParam("sort-by", defaultValue = "start") sortBy: String,
        @RequestParam("direction", defaultValue = "desc") sortDirection: SortDirection
    ): Page<PeriodDTO> {
        val paginatedPeriods = getPeriodApplicationService.getPaginatedPeriods(limit, offset, sortBy, sortDirection)
        return periodApiMapper.toDto(paginatedPeriods)
    }

    @GetMapping("/period/{id}")
    fun getPeriod(@RequestParam("id") id: String): PeriodDetailDTO {
        val period = getPeriodApplicationService.getPeriodById(PeriodId(id))
        return periodApiMapper.toPeriodDetailDto(period)
    }
}