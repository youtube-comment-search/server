package hello.youtubecommentsearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(WebClientResponseException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<?>> handleWebClientResponseException(WebClientResponseException ex) {
        HttpStatus statusCode = ex.getStatusCode();
        int errorCode;
        String errorMessage;

        if (statusCode == HttpStatus.NOT_FOUND) {
            // 404 Not Found 에러
            errorCode = 12;
            errorMessage = "Resource not found";
        } else if (statusCode == HttpStatus.BAD_REQUEST) {
            // 400 Bad Request 에러
            errorCode = 11;
            errorMessage = "Bad Request";
        } else if (statusCode.is4xxClientError()) {
            // 기타 4xx 에러
            errorCode = 19;
            errorMessage = "Client Error";
        } else if (statusCode.is5xxServerError()) {
            // 5xx 에러
            errorCode = 99;
            errorMessage = "Server Error";
        } else {
            // 기타 예외 처리
            errorCode = 0;
            errorMessage = "Unknown Error";
        }

        // 에러 메시지를 웹으로 전달
        String responseBody = ex.getResponseBodyAsString();
        String responseJson = String.format("{\"errorCode\": %d, \"errorMessage\": \"%s\", \"responseBody\": %s}", errorCode, errorMessage, responseBody);

        return Mono.just(ResponseEntity.status(statusCode).build());
    }
}
