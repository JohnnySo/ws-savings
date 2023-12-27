package org.soneira.savings.api.rest

import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/movement"], consumes = [MediaType.APPLICATION_JSON_VALUE])
class MovementController (val importMovementApplicationService: ImportMovementApplicationService){

    @PostMapping(value = ["/preview-file"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value="file") file: MultipartFile): ResponseEntity<String>{
        importMovementApplicationService.preview(file.inputStream, file.originalFilename ?: "")
        return ResponseEntity<String>(HttpStatusCode.valueOf(200))
    }

    @PutMapping(value = ["/import-file"])
    fun import(@RequestParam(value="fileId") fileId: String): ResponseEntity<String>{
        importMovementApplicationService.import(fileId)
        return ResponseEntity<String>(HttpStatusCode.valueOf(200))
    }
}