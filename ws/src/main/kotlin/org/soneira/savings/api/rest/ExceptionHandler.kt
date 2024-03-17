package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.ErrorDTO
import org.soneira.savings.domain.model.exception.DomainException
import org.soneira.savings.domain.model.exception.FileParseException
import org.soneira.savings.domain.model.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@ControllerAdvice
class ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    fun handleGeneralException(exception: Exception): ResponseEntity<ErrorDTO> {
        return ResponseEntity.status(INTERNAL_SERVER_ERROR.value()).body(
            ErrorDTO(
                INTERNAL_SERVER_ERROR.value(),
                INTERNAL_SERVER_ERROR.reasonPhrase, exception.message ?: "Unexpected error!"
            )
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [ResourceNotFoundException::class])
    @ResponseStatus(NOT_FOUND)
    fun handleNotFoundException(exception: Exception): ResponseEntity<ErrorDTO> {
        return ResponseEntity.status(NOT_FOUND.value()).body(
            ErrorDTO(
                NOT_FOUND.value(), NOT_FOUND.reasonPhrase,
                exception.message ?: "Resource not found exception"
            )
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [DomainException::class])
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handleDomainException(exception: DomainException): ResponseEntity<ErrorDTO> {
        return ResponseEntity.status(UNPROCESSABLE_ENTITY.value()).body(
            ErrorDTO(
                UNPROCESSABLE_ENTITY.value(), UNPROCESSABLE_ENTITY.reasonPhrase,
                exception.message ?: "Domain exception"
            )
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [FileParseException::class, IllegalArgumentException::class, MethodArgumentTypeMismatchException::class]
    )
    @ResponseStatus(BAD_REQUEST)
    fun handleBadRequest(exception: java.lang.Exception): ResponseEntity<ErrorDTO> {
        return ResponseEntity.status(BAD_REQUEST.value()).body(
            ErrorDTO(
                BAD_REQUEST.value(), BAD_REQUEST.reasonPhrase,
                exception.message ?: "Bad request"
            )
        )
    }
}