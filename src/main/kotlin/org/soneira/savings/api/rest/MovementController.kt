package org.soneira.savings.api.rest

import org.mapstruct.factory.Mappers
import org.soneira.savings.api.dto.FileDTO
import org.soneira.savings.api.mapper.FileMapper
import org.soneira.savings.domain.port.input.ImportMovementApplicationService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(value = ["/movement"], consumes = [MediaType.APPLICATION_JSON_VALUE])
class MovementController (val importMovementApplicationService: ImportMovementApplicationService){

    @PostMapping(value = ["/preview-file"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun previewFile(@RequestParam(value="file") file: MultipartFile): ResponseEntity<FileDTO>{
        val fileSaved = importMovementApplicationService.preview(file.inputStream, file.originalFilename ?: "")
        val fileMapper: FileMapper = Mappers.getMapper(FileMapper::class.java)
        return ResponseEntity<FileDTO>(fileMapper.toDto(fileSaved), HttpStatus.OK)
    }

    @PutMapping(value = ["/import-file"])
    fun import(@RequestParam(value="fileId") fileId: String): ResponseEntity<String>{
        importMovementApplicationService.import(fileId)
        return ResponseEntity<String>(HttpStatusCode.valueOf(200))
    }
}