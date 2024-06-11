package ticketing.ticket.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(PageantionException.class)
    public ResponseEntity<Map<String, String>> handlePageantionException(PageantionException e, HttpServletRequest request) {
        
        HttpHeaders responssHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("code", "400");
        errorMap.put("error type",httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errorMap, responssHeaders, httpStatus);
    }
}
