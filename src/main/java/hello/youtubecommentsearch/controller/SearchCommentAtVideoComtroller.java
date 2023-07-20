package hello.youtubecommentsearch.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import hello.youtubecommentsearch.dto.CommentVideoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import hello.youtubecommentsearch.service.SearchCommentAtVideoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/comment-video")
@Api(tags = {"Youtube Comment Search API Test"})
public class SearchCommentAtVideoComtroller {

    private final SearchCommentAtVideoService searchCommentAtVideoService;

    @Autowired
    public SearchCommentAtVideoComtroller(SearchCommentAtVideoService searchCommentAtVideoService) {
        this.searchCommentAtVideoService = searchCommentAtVideoService;
    }

    @ApiOperation(value = "비디오로 댓글 리스트 검색", notes = "비디오로 댓글 리스트 검색 API")
    @GetMapping
    public List<CommentVideoDTO> getCommentAtVideo(@RequestParam("videoId") String videoId) {
        try {
            return searchCommentAtVideoService.search(videoId);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
