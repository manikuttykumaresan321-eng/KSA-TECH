package com.schemeai;
import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.Map;
@RestControllerAdvice
public class ApiExceptionHandler {
 @ExceptionHandler(IllegalArgumentException.class) public ResponseEntity<?> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(Exception.class) public ResponseEntity<?> general(Exception e){return ResponseEntity.status(500).body(Map.of("message","Unexpected server error."));}
}