package inc.evil.d17map.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.RuntimeException
import javax.security.sasl.AuthenticationException

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(InvalidClassroomDataException::class)
    fun handleInvalidClassroomData(ex: InvalidClassroomDataException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleGenericException(ex: RuntimeException): ResponseEntity<String> {
        return ResponseEntity("An unexpected error occurred: ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(ClassroomNotFoundException::class)
    fun handleClassroomNotFound(ex: ClassroomNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(MissingParameterException::class)
    fun handleMissingParameter(ex: MissingParameterException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(InvalidEquipmentDataException::class)
    fun handleInvalidEquipmentData(ex: InvalidEquipmentDataException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException): ResponseEntity<String> {
        return ResponseEntity("Access denied: ${ex.message}", HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(ex: AuthenticationException): ResponseEntity<String> {
        return ResponseEntity("Authentication required: ${ex.message}", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFound(ex: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
    }
}