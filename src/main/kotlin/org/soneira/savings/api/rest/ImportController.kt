package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.api.mapper.FileApiMapper
import org.soneira.savings.domain.usecase.ImportMovementsUseCase
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
class ImportController(
    val importMovementsUseCase: ImportMovementsUseCase,
    val fileApiMapper: FileApiMapper
) {

    @PostMapping(value = ["/preview"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<FileDTO> {
        val fileSaved = importMovementsUseCase.preview(file.inputStream, file.originalFilename ?: "")
        return ResponseEntity.ok(fileApiMapper.toDto(fileSaved))
    }

    @PostMapping(value = ["/import/{fileId}"])
    fun import(@PathVariable(value = "fileId") fileId: String): ResponseEntity<String> {
        importMovementsUseCase.import(fileId)
        return ResponseEntity.ok("success")
    }
}