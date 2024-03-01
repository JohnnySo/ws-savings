package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.mapper.MovementApiMapper
import org.soneira.savings.application.usecase.EditMovementUseCaseImpl
import org.soneira.savings.domain.usecase.FindMovementUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MovementController(
    val findMovementUseCase: FindMovementUseCase,
    val editMovementApplicationServiceImpl: EditMovementUseCaseImpl,
    val movementApiMapper: MovementApiMapper
) {
    @GetMapping("/movements")
    fun getPeriods(
        @RequestParam("search-param", required = true) searchParam: String
    ): ResponseEntity<List<MovementDTO>> {
        val movements = findMovementUseCase.find(searchParam)
        return ResponseEntity.ok().body(movements.map { movementApiMapper.asMovementDTO(it) })
    }

    @PostMapping("/movement", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun save(@RequestBody movementDTO: MovementDTO): ResponseEntity<String> {
        editMovementApplicationServiceImpl.edit(movementApiMapper.asEditableMovement(movementDTO))
        return ResponseEntity.ok("success")
    }
}