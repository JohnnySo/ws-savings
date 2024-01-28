package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.EditableMovementDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.mapper.MovementApiMapper
import org.soneira.savings.domain.port.input.FindMovementApplicationService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MovementController(
    val findMovementApplicationService: FindMovementApplicationService,
    val movementApiMapper: MovementApiMapper
) {
    @GetMapping("/movements")
    fun getPeriods(
        @RequestParam("search-param", required = true) searchParam: String
    ): ResponseEntity<List<MovementDTO>> {
        val movements = findMovementApplicationService.find(searchParam)
        return if (movements.isEmpty()) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.ok().body(movements.map { movementApiMapper.toDto(it) })
        }
    }

    @PostMapping("/movement", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun save(@RequestBody movementDTO: EditableMovementDTO): ResponseEntity<String> {
        return ResponseEntity.ok("success")
    }
}