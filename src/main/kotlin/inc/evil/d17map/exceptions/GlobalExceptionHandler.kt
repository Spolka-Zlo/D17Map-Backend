package inc.evil.d17map.exceptions

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.support.DefaultMessageSourceResolvable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val logger = KotlinLogging.logger {}

@ControllerAdvice
class GlobalExceptionHandler {

    private fun buildErrorResponse(
        errorCode: ErrorCodes,
        message: String?,
        status: HttpStatus
    ): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(
            errorCode = errorCode,
            message = message ?: "An unexpected error occurred"
        )
        return ResponseEntity.status(status).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        logger.error(ex) { "Exception caught in GlobalExceptionHandler : ${ex.message}" }

        return when (ex) {
            is MethodArgumentNotValidException -> {
                val errors = ex.bindingResult.allErrors
                    .mapNotNull(DefaultMessageSourceResolvable::getDefaultMessage)
                    .sorted()
                    .joinToString(", ")
                buildErrorResponse(ErrorCodes.VALIDATION_ERROR, errors, HttpStatus.BAD_REQUEST)
            }

            is BindException -> {
                val errors = ex.allErrors
                    .mapNotNull(DefaultMessageSourceResolvable::getDefaultMessage)
                    .sorted()
                    .joinToString(", ")
                buildErrorResponse(ErrorCodes.VALIDATION_ERROR, errors, HttpStatus.BAD_REQUEST)
            }

            is NotFoundException -> buildErrorResponse(
                ErrorCodes.NOT_FOUND_ERROR,
                ex.message ?: "Resource not found",
                HttpStatus.NOT_FOUND
            )

            is UserAlreadyExistsException -> buildErrorResponse(
                ErrorCodes.ALREADY_EXISTS_ERROR,
                ex.message,
                HttpStatus.CONFLICT
            )

            is AuthenticationException, is AccessDeniedException -> {
                logger.warn(ex) { "Security exception rethrown: $ex" }
                throw ex // Rethrow security-related exceptions
            }

            else -> buildErrorResponse(
                ErrorCodes.UNKNOWN_ERROR,
                ex.message,
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}
