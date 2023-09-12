package hello.youtubecommentsearch.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import hello.youtubecommentsearch.exception.ErrorResponse;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler(value = {WebClientResponseException.class})
    public ResponseEntity<?> handleWebClientResponseException(WebClientResponseException ex) {
        HttpStatus statusCode = ex.getStatusCode();
        int errorCode;
        String errorMessage;

        if (statusCode == HttpStatus.NOT_FOUND) {
            // 404 Not Found 에러
            errorCode = 12;
            errorMessage = "Resource not found";
            log.info("Resource not found");
            return ResponseEntity
                    .status(statusCode)
                    .body(ErrorResponse.builder()
                            .errorCode(errorCode)
                            .errorMessage(errorMessage)
                            .build()
                    );
        } else if (statusCode == HttpStatus.BAD_REQUEST) {
            // 400 Bad Request 에러
            errorCode = 11;
            errorMessage = "Bad Request";
            log.info("Bad Request");
            return ResponseEntity
                    .status(statusCode)
                    .body(ErrorResponse.builder()
                            .errorCode(errorCode)
                            .errorMessage(errorMessage)
                            .build()
                    );
        } else if (statusCode.is4xxClientError()) {
            // 기타 4xx 에러
            errorCode = 19;
            errorMessage = "Client Error";
            log.info("other 4xx Error");
            return ResponseEntity
                    .status(statusCode)
                    .body(ErrorResponse.builder()
                            .errorCode(errorCode)
                            .errorMessage(errorMessage)
                            .build()
                    );
        } else if (statusCode.is5xxServerError()) {
            // 5xx 에러
            errorCode = 99;
            errorMessage = "Server Error";
            log.info("other 5xx Error");
            return ResponseEntity
                    .status(statusCode)
                    .body(ErrorResponse.builder()
                            .errorCode(errorCode)
                            .errorMessage(errorMessage)
                            .build()
                    );
        } else {
            // 기타 예외 처리
            errorCode = 0;
            errorMessage = "Unknown Error";
            log.info("Unknown Error");
            return ResponseEntity
                    .status(statusCode)
                    .body(ErrorResponse.builder()
                            .errorCode(errorCode)
                            .errorMessage(errorMessage)
                            .build()
                    );
        }

    }
}
