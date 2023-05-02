package br.com.ffscompany.easyorder.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

/**
 * Global exception handler for the application.
 * <p>
 * Any exception thrown by a controller method will be handled by this class.
 */
@RestControllerAdvice
public class HttpErrorHandler {

    /**
     * Handles EntityNotFoundException and returns a 404 Not Found response.
     *
     * @return HTTP 404 response.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    /**
     * Handles MethodArgumentNotValidException and returns a 400 Bad Request response.
     *
     * @param ex exception containing details of the validation error(s).
     * @return HTTP 400 response with a list of validation errors.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var fieldErrors = ex.getFieldErrors().stream()
                .map(FieldErrorDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.badRequest().body(fieldErrors);
    }

    /**
     * Handles MethodArgumentTypeMismatchException and returns a 400 Bad Request response.
     *
     * @param ex exception containing details of the type mismatch error.
     * @return HTTP 400 response with the details of the type mismatch error.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        var fieldError = new FieldErrorDTO(ex.getName(), ex.getMessage());

        return ResponseEntity.badRequest().body(fieldError);
    }

    /**
     * Handles HttpMessageNotReadableException and returns a 400 Bad Request response.
     *
     * @param ex exception containing details of the message not readable error.
     * @return HTTP 400 response with the details of the message not readable error.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        var fieldError = new FieldErrorDTO(null, ex.getMessage());

        return ResponseEntity.badRequest().body(fieldError);
    }

    /**
     * Handles HttpRequestMethodNotSupportedException and returns a 405 Method Not Allowed response.
     *
     * @param ex exception containing details of the unsupported request method.
     * @return HTTP 405 response with the details of the unsupported request method.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(ex.getBody());
    }

    /**
     * Handles InvalidDataAccessApiUsageException and returns a 500 Internal Server Error response.
     *
     * @param ex exception containing details of the invalid data access API usage error.
     * @return HTTP 500 response with the details of the invalid data access API usage error.
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex) {
        var fieldError = new FieldErrorDTO(null, ex.getMessage());

        return ResponseEntity.internalServerError().body(fieldError);
    }


    /**
     * Handles NullPointerException by returning an internal server error response with a FieldErrorDTO object containing the exception message.
     * @param ex the NullPointerException
     * @return a ResponseEntity with an internal server error status and a FieldErrorDTO object
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.internalServerError().body(new FieldErrorDTO(null, ex.getMessage()));
    }

    /**
     * Handles DataIntegrityViolationException by returning a conflict response with a FieldErrorDTO object containing the exception message.
     * @param ex the DataIntegrityViolationException
     * @return a ResponseEntity with a conflict status and a FieldErrorDTO object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleHttpDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new FieldErrorDTO(null, ex.getMessage()));
    }

    /**
     * A DTO object representing a field error with a field name and a message.
     */
    private record FieldErrorDTO(String fieldName, String message) {

        /**
         * Constructs a FieldErrorDTO object from a Spring FieldError object.
         * @param erro the FieldError object
         */
        private FieldErrorDTO(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }

        /**
         * Constructs a FieldErrorDTO object with the specified field name and message.
         * @param fieldName the name of the field with an error
         * @param message the error message
         */
        private FieldErrorDTO(String fieldName, String message) {
            this.fieldName = fieldName;
            this.message = message;
        }
    }

}
