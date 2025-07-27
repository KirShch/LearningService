package exception;

import com.example.LearningService.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class LearningServiceExceptionHandler {

    private ErrorResponseDto getResponse(LearningServiceException ex, WebRequest request){
        return new ErrorResponseDto(
                ex.getStatus(),
                ex.getMessage(),
                request.getDescription(false).replace("uri=", "")
                );
    }

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserEmailExistsException ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }

    @ExceptionHandler(UserIncorrectRoleException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(UserIncorrectRoleException ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(CourseNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(getResponse(ex, request), ex.getStatus());
    }
}
