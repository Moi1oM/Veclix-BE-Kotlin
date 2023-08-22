package paxHumana.veclix.common.result

import org.springframework.http.HttpStatus

data class ErrorResult (
    val code: HttpStatus,
    val message: String?,
)