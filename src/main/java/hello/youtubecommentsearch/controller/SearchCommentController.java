package hello.youtubecommentsearch.controller;

import hello.youtubecommentsearch.dto.CommentDTO;
import hello.youtubecommentsearch.exception.CustomExceptionHandler;
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
    @GetMapping
    public Mono<ResponseEntity<List<CommentDTO>>> getComment(
            @RequestParam("id") String id,
            @RequestParam("type") String type,
            @RequestParam(name = "includeKeyword", required = false) String includeKeyword,
            @RequestParam(name = "excludeKeyword", required = false) String excludeKeyword) {
        return searchCommentService.search(id, type, includeKeyword, excludeKeyword)
                .map(CommentDTOList -> ResponseEntity.ok(CommentDTOList));
    }

    @GetMapping("/next")
    public Mono<ResponseEntity<List<CommentDTO>>> getComment1(String id, String type, String includeKeyword, String excludeKeyword) {
        return searchCommentService.getAllComments(id, type, includeKeyword, excludeKeyword)
                .map(CommentDTOList -> ResponseEntity.ok(CommentDTOList));
    }

}