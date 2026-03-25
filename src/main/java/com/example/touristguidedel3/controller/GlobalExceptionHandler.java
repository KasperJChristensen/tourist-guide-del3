package com.example.touristguidedel3.controller;




import com.example.touristguidedel3.exception.AttractionNotFound;
import com.example.touristguidedel3.exception.DatabaseOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(AttractionNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlerAttractionNotFound(AttractionNotFound exception, Model model) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("error", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", exception.getMessage());

        return "error/404";
    }

    @ExceptionHandler(DatabaseOperationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handlerDatabaseOperation(DatabaseOperationException exception, Model model) {
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        model.addAttribute("message", "A database error has occurred");

        return "error/500";
    }
}
