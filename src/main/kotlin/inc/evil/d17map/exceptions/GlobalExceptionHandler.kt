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
import java.util.stream.Collectors

private val logger = KotlinLogging.logger {}


@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(ex: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val error = ex.bindingResult.allErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .sorted()
            .collect(Collectors.joining(", "))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(error))
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(ex: BindException): ResponseEntity<ErrorResponse> {
        val error = ex.allErrors.stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .sorted()
            .collect(Collectors.joining(", "))
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(error))
    }

    @ExceptionHandler(InvalidClassroomDataException::class)
    fun handleInvalidClassroomData(ex: InvalidClassroomDataException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "Invalid classroom data"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(ClassroomNotFoundException::class)
    fun handleClassroomNotFound(ex: ClassroomNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "Classroom not found"), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MissingParameterException::class)
    fun handleMissingParameter(ex: MissingParameterException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "Required parameter is missing"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidEquipmentDataException::class)
    fun handleInvalidEquipmentData(ex: InvalidEquipmentDataException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "Invalid equipment data"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "User not found"), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(ErrorResponse(ex.message ?: "User already exists"), HttpStatus.CONFLICT)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleGenericException(ex: RuntimeException): ResponseEntity<ErrorResponse> {
        logger.error(ex) { "Generic Exception caught: ${ex.message}" }
        if (ex is AuthenticationException || ex is AccessDeniedException)
            throw ex

        return ResponseEntity(
            ErrorResponse("An unexpected error occurred: ${ex.message}"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}
