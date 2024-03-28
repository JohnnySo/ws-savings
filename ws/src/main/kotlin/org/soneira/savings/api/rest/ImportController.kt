package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.api.dto.MovementDTO
import org.soneira.savings.api.mapper.FileApiMapper
import org.soneira.savings.domain.model.vo.id.FileId
import org.soneira.savings.domain.usecase.GetFilesUseCase
import org.soneira.savings.domain.usecase.ImportMovementsUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/preview")
class ImportController(
    val importMovementsUseCase: ImportMovementsUseCase,
    val getFilesUseCase: GetFilesUseCase,
    val fileApiMapper: FileApiMapper
) {

    @GetMapping
    fun get(): ResponseEntity<List<FileDTO>> {
        return ResponseEntity.ok(getFilesUseCase.getFiles().map(fileApiMapper::asFileDTO))
    }

    @PutMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<FileDTO> {
        val fileSaved = importMovementsUseCase.preview(file.inputStream, file.originalFilename ?: "")
        return ResponseEntity.ok(fileApiMapper.asFileDTO(fileSaved))
    }

    @GetMapping(value = ["/{id}"])
    fun detail(@PathVariable(value = "id") id: String): ResponseEntity<FileDTO> {
        return ResponseEntity.ok(fileApiMapper.asFileDTO(getFilesUseCase.getFile(FileId(id))))
    }

    @PostMapping(value = ["/{id}/import"])
    fun import(@PathVariable(value = "id") id: String): ResponseEntity.HeadersBuilder<*> {
        importMovementsUseCase.import(FileId(id))
        return ResponseEntity.noContent()
    }

    @PostMapping(value = ["/{id}/edit"])
    fun edit(
        @PathVariable(value = "id") id: String,
        @RequestBody movementDTO: List<MovementDTO>
    ): ResponseEntity<String> {
        TODO("implement edit of several preview movements at one time without id. Possibly necessary another type instead of MovementDTO")
    }
}