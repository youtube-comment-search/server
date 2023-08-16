package hello.youtubecommentsearch.controller;

import hello.youtubecommentsearch.dto.CommentVideoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchCommentAtVideoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/search/comment/video")
@Tag(name = "Youtube Comment Search API Test", description = "API Document")
//@Api(tags = {"Youtube Comment Search API Test"})
public class SearchCommentAtVideoComtroller {

    private final SearchCommentAtVideoService searchCommentAtVideoService;

    @Autowired
    public SearchCommentAtVideoComtroller(SearchCommentAtVideoService searchCommentAtVideoService) {
        this.searchCommentAtVideoService = searchCommentAtVideoService;
    }

    @Operation(summary = "비디오로 댓글 리스트 검색", description = "비디오로 댓글 리스트 검색 API")
    @GetMapping
    public Mono<ResponseEntity<List<CommentVideoDTO>>> getCommentAtVideo(@RequestParam("videoId") String videoId) {
        return searchCommentAtVideoService.search(videoId)
                .map(commentVideoDTOList -> ResponseEntity.ok(commentVideoDTOList))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus statusCode = ex.getStatusCode();
                    if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                        return Mono.just(ResponseEntity.status(statusCode).build());
                    } else {
                        return Mono.error(ex);
                    }
                });
    }
}
