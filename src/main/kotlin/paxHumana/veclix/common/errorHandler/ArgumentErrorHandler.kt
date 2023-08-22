package paxHumana.veclix.common.errorHandler

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import paxHumana.veclix.common.result.ErrorResult

@RestControllerAdvice
class ArgumentErrorHandler {
    private val log = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(value = [IllegalStateException::class, IllegalArgumentException::class])
    fun handleIllegalStateError(e: Exception): ResponseEntity<ErrorResult> {
        log.error("${e.message}")
        return ResponseEntity(
            ErrorResult(
                code = HttpStatus.BAD_REQUEST,
                message = "BAD REQUEST! original message : ${e.message}",
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseError(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
        val body = mutableMapOf<String, Any>()
        body["status"] = "error"
        body["full_message"] = ex.stackTrace
        // Extracting the name of the property that caused the issue from the exception message.
        // This assumes the error message is consistent in format with the one you provided.
        val matchResult = Regex("JSON property (\\w+) due to missing").find(ex.message ?: "")
        val propertyName = matchResult?.groups?.get(1)?.value

        if (propertyName != null) {
            body["message"] = "Missing or invalid property in request body: $propertyName"
        } else {
            // Fallback if we couldn't extract the property name from the error message
            body["message"] = "Invalid request body"
        }

        return ResponseEntity.badRequest().body(body)
    }

}