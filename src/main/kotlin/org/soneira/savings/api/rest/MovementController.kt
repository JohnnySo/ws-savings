package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.EditableMovementDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.mapper.PeriodApiMapper
import org.soneira.savings.domain.port.input.GetPeriodApplicationService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MovementController(
    val getPeriodApplicationService: GetPeriodApplicationService,
    val periodApiMapper: PeriodApiMapper
) {
    @GetMapping("/movements")
    fun getPeriods(@RequestParam("sort-by", required = true) searchParam: String): ResponseEntity<MovementDTO> {
        TODO("Not implemented yet")
    }

    @PostMapping("/movement", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun save(@RequestBody movementDTO: EditableMovementDTO): ResponseEntity<String> {
        return ResponseEntity.ok("success")
    }
}