package org.soneira.savings.api.rest

import org.soneira.savings.domain.usecase.GetMasterDataUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/master"], produces = [MediaType.APPLICATION_JSON_VALUE])
class MasterController(val getMasterDataUseCase: GetMasterDataUseCase) {
    @GetMapping("/years")
    fun getYears(): ResponseEntity<List<Int>> {
        return ResponseEntity.ok(this.getMasterDataUseCase.getYears())
    }
}