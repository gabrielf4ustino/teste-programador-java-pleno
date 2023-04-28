package br.com.ffscompany.marketplacemanager.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HttpErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity NotFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity badRequestHandler(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(ex.getFieldErrors().stream().map(errorDTO::new).toList());
    }

    private record errorDTO(String fieldName, String message) {
        private errorDTO(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}
