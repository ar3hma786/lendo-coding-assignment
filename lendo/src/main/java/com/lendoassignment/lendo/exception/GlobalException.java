package com.lendoassignment.lendo.exception;



import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(UsersException.class)
    public ResponseEntity<ErrorDetails> handleUsersException(UsersException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            webRequest.getDescription(false),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(PostsException.class)
    public ResponseEntity<ErrorDetails> handleUsersException(PostsException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            webRequest.getDescription(false),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(CommentsException.class)
    public ResponseEntity<ErrorDetails> handleUsersException(CommentsException exception, WebRequest webRequest) {
        ErrorDetails errorDetails = new ErrorDetails(
            exception.getMessage(),
            webRequest.getDescription(false),
            LocalDateTime.now()
        );
        
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    


}
