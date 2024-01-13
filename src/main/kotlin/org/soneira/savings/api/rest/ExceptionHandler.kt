package org.soneira.savings.api.rest

import org.soneira.savings.api.dto.ErrorDTO
import org.soneira.savings.domain.exception.DomainException
import org.soneira.savings.domain.exception.FileParseException
import org.soneira.savings.domain.exception.ResourceNotFoundException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
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
    fun handleGeneralException(exception: Exception): ErrorDTO {
        return ErrorDTO(
            INTERNAL_SERVER_ERROR.value(),
            INTERNAL_SERVER_ERROR.reasonPhrase, exception.message ?: "Unexpected error!"
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [ResourceNotFoundException::class])
    @ResponseStatus(NOT_FOUND)
    fun handleNotFoundException(exception: Exception): ErrorDTO {
        return ErrorDTO(
            NOT_FOUND.value(), NOT_FOUND.reasonPhrase,
            exception.message ?: "Resource not found exception"
        )
    }

    @ResponseBody
    @ExceptionHandler(value = [DomainException::class])
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    fun handleDomainException(exception: DomainException): ErrorDTO {
        return ErrorDTO(
            UNPROCESSABLE_ENTITY.value(),
            UNPROCESSABLE_ENTITY.reasonPhrase,
            exception.message ?: "Domain exception"
        )
    }

    @ResponseBody
    @ExceptionHandler(
        value = [FileParseException::class, IllegalArgumentException::class, MethodArgumentTypeMismatchException::class]
    )
    @ResponseStatus(BAD_REQUEST)
    fun handleBadRequest(exception: java.lang.Exception): ErrorDTO {
        return ErrorDTO(
            BAD_REQUEST.value(),
            BAD_REQUEST.reasonPhrase,
            exception.message ?: "Bad request"
        )
    }
}