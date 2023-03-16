package com.nextuple.orderpricingapp.exception;


import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

//import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{
	
    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ApiError entityNotFoundException(EntityNotFoundException exception){
        return new ApiError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
	}
    
    @ExceptionHandler(value = DuplicateEntityException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ApiError duplicateEntityException(DuplicateEntityException exception){
        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
	}
    
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleMethodArgumentTypeMisMatch(MethodArgumentTypeMismatchException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
        
    
    @ExceptionHandler(value = DateTimeParseException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleDateParseException(DateTimeParseException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
//        HttpStatus status, WebRequest request) {
//      Map<String, List<String>> body = new HashMap<>();
//
//      List<String> errors = ex.getBindingResult()
//          .getFieldErrors()
//          .stream()
//          .map(DefaultMessageSourceResolvable::getDefaultMessage)
//          .collect(Collectors.toList());
//
//        List<String> errorsSort = new ArrayList<>(
//                new LinkedHashSet<>(errors));
//      body.put("errors", errorsSort);
//
//      return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//    }
    
    
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiError conflict(DataIntegrityViolationException e) {

        String message = NestedExceptionUtils.getMostSpecificCause(e).getMessage();
        return new ApiError(HttpStatus.BAD_REQUEST.value(), message);

    }

    
//    @ExceptionHandler(value = ConstraintViolationException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public @ResponseBody ApiError handleConstraint(ConstraintViolationException exception) {
//        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
//    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public @ResponseBody ApiError handleException(RuntimeException exception) {
        System.out.println("ApiExceptionHandler.handleException");
        return new ApiError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }
   
    
	
}
