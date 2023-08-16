package hello.youtubecommentsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.youtubecommentsearch.dto.CommentChannelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchCommentAtChannelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/search/comment/channel")
@Tag(name = "Youtube Comment Search API Test", description = "API Document")
public class SearchCommentAtChannelController {

    private final SearchCommentAtChannelService searchCommentAtChannelService;

    @Autowired
    public SearchCommentAtChannelController(SearchCommentAtChannelService searchCommentAtChannelService) {
        this.searchCommentAtChannelService = searchCommentAtChannelService;
    }

    @Operation(summary = "채널 댓글 리스트 검색", description = "채널에 있는 모든 댓글 리스트 검색 API.")
    @GetMapping
    public Mono<ResponseEntity<List<CommentChannelDTO>>> getCommentAtChannel(@RequestParam("channelId") String channelId) {
        return searchCommentAtChannelService.getAllComments(channelId)
                 .map(commentChannelDTOList -> ResponseEntity.ok(commentChannelDTOList))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus statusCode = ex.getStatusCode();
                    if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                        return Mono.just(ResponseEntity.status(statusCode).build());
                    } else {
                        return Mono.error(ex);
                    }
                });
    }

    @GetMapping("/keyword")
    public Mono<ResponseEntity<List<CommentChannelDTO>>> getCommentAtChannel1(@RequestParam("channelId") String channelId, @RequestParam("keyword") String keyword) {
        return searchCommentAtChannelService.search(channelId)
                .map(commentChannelDTOList -> filterCommentsByKeyword(commentChannelDTOList, keyword))
                .map(filteredComments -> ResponseEntity.ok(filteredComments))
                // .map(commentChannelDTOList -> ResponseEntity.ok(commentChannelDTOList))
                .onErrorResume(WebClientResponseException.class, ex -> {
                    HttpStatus statusCode = ex.getStatusCode();
                    if (statusCode.is4xxClientError() || statusCode.is5xxServerError()) {
                        return Mono.just(ResponseEntity.status(statusCode).build());
                    } else {
                        return Mono.error(ex);
                    }
                });
    }


    // @GetMapping("/keyword")
    private List<CommentChannelDTO> filterCommentsByKeyword(List<CommentChannelDTO> comments, String keyword) {
        return comments.stream()
                .filter(comment -> comment.getTextOriginal().contains(keyword))
                .collect(Collectors.toList());
    }
}
