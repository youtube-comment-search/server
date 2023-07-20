package hello.youtubecommentsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import hello.youtubecommentsearch.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class SearchCommentAtVideoService {
    @Value("${youtube-api-key}")
    private String DEVELOPER_KEY;
    private static final String API_URL = "https://youtube.googleapis.com/youtube/v3/commentThreads";
    private static final String PART = "snippet, replies";
//    private String videoId = "XivpMCkkafI";
    private final WebClient webClient;

    public SearchCommentAtVideoService() {
        this.webClient = WebClient.create();
    }

    public List<CommentVideoDTO> search(String videoId) throws JsonProcessingException {
        String url = String.format("%s?part=%s&videoId=%s&key=%s", API_URL, PART, videoId, DEVELOPER_KEY);
        Mono<JsonNode> response = webClient.get()
                .uri(url)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> ...)
//		        .onStatus(HttpStatus::is5xxServerError, response -> ...)
                .bodyToMono(JsonNode.class);
        JsonNode jsonNode = response.block();
        List<CommentVideoDTO> commentVideoDTOList = new ArrayList<>();
        if (jsonNode != null && jsonNode.has("items") && jsonNode.get("items").isArray()) {
            for (JsonNode item : jsonNode.get("items")) {
                CommentVideoDTO commentVideoDTO = initCommentVideo(item);
                commentVideoDTOList.add(commentVideoDTO);
            }
        }
        return commentVideoDTOList;
    }

    private CommentVideoDTO initCommentVideo(JsonNode item) {
        CommentVideoDTO commentVideoDTO = new CommentVideoDTO();

        JsonNode snippet = item.get("snippet").get("topLevelComment").get("snippet");
        commentVideoDTO.setAuthorDisplayName(snippet.get("authorDisplayName").asText());
        commentVideoDTO.setAuthorChannelId(snippet.get("authorChannelId").get("value").asText());
        commentVideoDTO.setPublishedAt(snippet.get("publishedAt").asText());
        commentVideoDTO.setLikeCount(snippet.get("likeCount").asInt());
        commentVideoDTO.setTextOriginal(snippet.get("textOriginal").asText());
        commentVideoDTO.setTextDisplay(snippet.get("textDisplay").asText());

        return commentVideoDTO;
    }
}
