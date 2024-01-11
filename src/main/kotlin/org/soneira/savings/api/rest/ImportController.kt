package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.api.mapper.FileApiMapper
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
class ImportController(
    val importMovementApplicationService: ImportMovementApplicationService,
    val fileApiMapper: FileApiMapper
) {

    @PostMapping(value = ["/preview"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value = "file") file: MultipartFile): ResponseEntity<FileDTO> {
        val fileSaved = importMovementApplicationService.preview(file.inputStream, file.originalFilename ?: "")
        return ResponseEntity<FileDTO>(fileApiMapper.toDto(fileSaved), HttpStatus.OK)
    }

    @PutMapping(value = ["/import/{fileId}"])
    fun import(@PathVariable(value = "fileId") fileId: String): ResponseEntity<String> {
        importMovementApplicationService.import(fileId)
        return ResponseEntity<String>(HttpStatusCode.valueOf(200))
    }
}