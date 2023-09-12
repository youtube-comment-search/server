package hello.youtubecommentsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.youtubecommentsearch.dto.ChannelVideoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchChannelVideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/search/channel-video")
@Tag(name = "Youtube Comment Search API Test", description = "API Document")
public class SearchChannelVideoController {

    private final SearchChannelVideoService searchChannelVideoService;

    @Autowired
    public SearchChannelVideoController(SearchChannelVideoService searchChannelVideoService) {
        this.searchChannelVideoService = searchChannelVideoService;
    }

    @Operation(summary = "채널 비디오 검색", description = "채널명, 영상 이름으로 검색 API")
    @GetMapping
    public Mono<ResponseEntity<ChannelVideoResponseDTO>> getChannelVideo(@RequestParam("query") String query) {
        return searchChannelVideoService.search(query)
                .map(ChannelVideoResponseDTO -> ResponseEntity.ok(ChannelVideoResponseDTO));
    }
}
