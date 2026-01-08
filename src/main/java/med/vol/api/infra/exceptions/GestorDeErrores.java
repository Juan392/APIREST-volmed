package med.vol.api.infra.exceptions;

import jakarta.persistence.EntityNotFoundException;
import med.vol.api.domain.ValidacionExcepcion;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Especial para gestionar errores
@RestControllerAdvice
public class GestorDeErrores {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity error404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity error400(MethodArgumentNotValidException error){
        var errores = error.getFieldErrors();
        return ResponseEntity.badRequest().body(errores.stream().map(datosErrorValidacion::new).toList());
    }

    @ExceptionHandler(ValidacionExcepcion.class)
    public ResponseEntity tratarErrorDeValidaciones(ValidacionExcepcion e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public record datosErrorValidacion(
            String campo,
            String mensaje
    ){
        public datosErrorValidacion(FieldError error){
            this(
                    error.getField(), error.getDefaultMessage()
            );
        }
    }
}
