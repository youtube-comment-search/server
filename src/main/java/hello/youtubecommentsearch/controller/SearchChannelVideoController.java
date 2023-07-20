package hello.youtubecommentsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.youtubecommentsearch.dto.ChannelVideoResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchChannelVideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search/channel-video")
@Api(tags = {"Youtube Comment Search API Test"})
public class SearchChannelVideoController {

    private final SearchChannelVideoService searchChannelVideoService;

    @Autowired
    public SearchChannelVideoController(SearchChannelVideoService searchChannelVideoService) {
        this.searchChannelVideoService = searchChannelVideoService;
    }

    @ApiOperation(value = "채널, 비디오 검색", notes = "맨 처음 검색하면 나오는 채널과 비디오 리스트를 불러오는 API")
    @GetMapping
    public ChannelVideoResponseDTO getChannelVideoSearch(@RequestParam("query") String query) {
        try {
            return searchChannelVideoService.search(query);
        } catch (JsonProcessingException e) {
            return new ChannelVideoResponseDTO("Error occurred during JSON processing");
        }
    }
}
