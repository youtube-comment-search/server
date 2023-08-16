package hello.youtubecommentsearch.controller;

import hello.youtubecommentsearch.dto.CommentChannelDTO;
import hello.youtubecommentsearch.dto.CommentDTO;
import hello.youtubecommentsearch.exception.CustomExceptionHandler;
import hello.youtubecommentsearch.service.SearchCommentAtChannelService;
import hello.youtubecommentsearch.service.SearchCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/comment")
@Tag(name = "Youtube Comment Search API Test", description = "API Document")
public class SearchCommentController {

    private final SearchCommentService searchCommentService;

    @Autowired
    public SearchCommentController(SearchCommentService searchCommentService) {
        this.searchCommentService = searchCommentService;
    }

    @Operation(summary = "채널 댓글 리스트 검색", description = "채널에 있는 모든 댓글 리스트 검색 API.")
//    @GetMapping
//    public Mono<ResponseEntity<List<CommentDTO>>> getCommentAtChannel(@RequestParam("channelId") String channelId) {
//        return searchCommentService.getAllComments(channelId)
//                .map(CommentDTOList -> ResponseEntity.ok(CommentDTOList))
//                .onErrorResume(WebClientResponseException.class, ex -> {
//                    HttpStatus statusCode = ex.getStatusCode();
//                    if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
//                        return Mono.just(ResponseEntity.status(statusCode).build());
//                    } else {
//                        return Mono.error(ex);
//                    }
//                });
//    }

    @GetMapping
    public Mono<ResponseEntity<List<CommentDTO>>> getComment(
            @RequestParam("id") String id,
            @RequestParam("type") String type,
            @RequestParam(name = "includeKeyword", required = false) String includeKeyword,
            @RequestParam(name = "excludeKeyword", required = false) String excludeKeyword) {
        return searchCommentService.search(id, type, includeKeyword, excludeKeyword)
//                .map(CommentDTOList -> filterCommentsByKeyword(commentDTOList, keyword))
//                .map(filteredComments -> ResponseEntity.ok(filteredComments))
                .map(CommentDTOList -> ResponseEntity.ok(CommentDTOList));
//                .onErrorResume(ex -> {
//                    // handle error here if needed
//                    // 여기서 에러 처리를 추가로 할 수 있음
//                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
//                });
//                .onErrorResume(WebClientResponseException.class, ex -> {
//                    HttpStatus statusCode = ex.getStatusCode();
//                    if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
//                        return Mono.just(ResponseEntity.status(statusCode).build());
//                    } else {
//                        return Mono.error(ex);
//                    }
//                });
    }

    public Mono<ResponseEntity<List<CommentDTO>>> getComment1(String id, String type, String includeKeyword, String excludeKeyword) {
        return searchCommentService.search(id, type, includeKeyword, excludeKeyword)
                .map(CommentDTOList -> ResponseEntity.ok(CommentDTOList));
    }
//
//    @ExceptionHandler(WebClientResponseException.class)
//    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<?>> handleWebClientResponseException(WebClientResponseException ex) {
        HttpStatus statusCode = ex.getStatusCode();
        String responseBody = "exception handler test";
        return Mono.just(ResponseEntity.status(statusCode).body(responseBody));
    }


}