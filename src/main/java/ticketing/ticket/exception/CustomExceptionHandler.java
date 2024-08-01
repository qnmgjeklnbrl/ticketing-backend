package ticketing.ticket.exception;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import slack.alarm.aop.annotation.SlackNotification;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(PageanationException.class)
    //@SlackNotification
    public ResponseEntity<Map<String, String>> handlePageantionException(PageanationException e, HttpServletRequest request) {
        
        HttpHeaders responssHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("code", "400");
        errorMap.put("error type",httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errorMap, responssHeaders, httpStatus);
    }

    @ExceptionHandler(DuplicatedCouponException.class)
    public ResponseEntity<Map<String, String>> handleDuplicatedCouponException(DuplicatedCouponException e, HttpServletRequest request) {
        HttpHeaders responssHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("code", "400");
        errorMap.put("error type",httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errorMap, responssHeaders, httpStatus);
    }

    @ExceptionHandler(DuplicationReservationException.class)
    public ResponseEntity<Map<String, String>> handleDuplicationReservationException(DuplicationReservationException e, HttpServletRequest request) {
        HttpHeaders responssHeaders = new HttpHeaders();
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("errorMessage", e.getMessage());
        errorMap.put("code", "400");
        errorMap.put("error type",httpStatus.getReasonPhrase());
        return new ResponseEntity<>(errorMap, responssHeaders, httpStatus);
    }
}
