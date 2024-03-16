package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.mapper.FileApiMapper
import org.soneira.savings.domain.usecase.ImportMovementsUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/preview")
class ImportController(
    val importMovementsUseCase: ImportMovementsUseCase,
    val fileApiMapper: FileApiMapper
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<FileDTO> {
        val fileSaved = importMovementsUseCase.preview(file.inputStream, file.originalFilename ?: "")
        return ResponseEntity.ok(fileApiMapper.asFileDTO(fileSaved))
    }

    @PostMapping(value = ["/{fileId}/import"])
    fun import(@PathVariable(value = "fileId") fileId: String): ResponseEntity<String> {
        importMovementsUseCase.import(fileId)
        return ResponseEntity.ok("success")
    }

    @GetMapping
    fun find(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<FileDTO> {
        TODO("List all the files that are pending to import")
    }

    @GetMapping(value = ["/{fileId}"])
    fun detail(@PathVariable(value = "fileId") fileId: String): ResponseEntity<FileDTO> {
        TODO("Get the detail of a file")
    }

    @PostMapping(value = ["/{fileId}/edit"])
    fun edit(
        @PathVariable(value = "fileId") fileId: String,
        @RequestBody movementDTO: List<MovementDTO>
    ): ResponseEntity<String> {
        TODO("implement edit of several preview movements at one time without id. Possibly necessary another type instead of MovementDTO")
    }
}