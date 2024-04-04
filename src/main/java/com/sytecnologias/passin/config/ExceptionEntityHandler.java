package com.sytecnologias.passin.config;

import com.sytecnologias.passin.domain.event.exceptions.EventNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionEntityHandler {
    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity handlerEventNotFoundException(EventNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
}
