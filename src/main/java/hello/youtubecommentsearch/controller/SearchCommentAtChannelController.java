package hello.youtubecommentsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.youtubecommentsearch.dto.CommentChannelDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchCommentAtChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/comment-channel")
@Api(tags = {"Youtube Comment Search API Test"})
public class SearchCommentAtChannelController {

    private final SearchCommentAtChannelService searchCommentAtChannelService;

    @Autowired
    public SearchCommentAtChannelController(SearchCommentAtChannelService searchCommentAtChannelService) {
        this.searchCommentAtChannelService = searchCommentAtChannelService;
    }

    @ApiOperation(value = "채널 댓글 리스트 검색", notes = "채널에 있는 모든 댓글 리스트 검색 API")
    @GetMapping
    public List<CommentChannelDTO> getCommentAtChannel(@RequestParam("channelId") String channelId) throws JsonProcessingException {
        return searchCommentAtChannelService.search(channelId);
    }
}
