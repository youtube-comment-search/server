package hello.youtubecommentsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import hello.youtubecommentsearch.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class SearchCommentAtChannelService {
    @Value("${youtube-api-key}")
    private String DEVELOPER_KEY;
    private static final String API_URL = "https://youtube.googleapis.com/youtube/v3/commentThreads";
    private static final String PART = "snippet, replies";
//    private String channelId = "UCrpoE9e2-eWcj8AYvwYdebw";
    private final WebClient webClient;

    public SearchCommentAtChannelService() {
        this.webClient = WebClient.create();
    }

    public List<CommentChannelDTO> search(String channelId) throws JsonProcessingException {
        String url = String.format("%s?part=%s&allThreadsRelatedToChannelId=%s&key=%s", API_URL, PART, channelId, DEVELOPER_KEY);
        Mono<JsonNode> response = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class);

        JsonNode jsonNode = response.block();
        List<CommentChannelDTO> commentChannelDTOList = new ArrayList<>();
        if (jsonNode != null && jsonNode.has("items") && jsonNode.get("items").isArray()) {
            for (JsonNode item : jsonNode.get("items")) {
                CommentChannelDTO commentChannelDTO = initCommentChannel(item);
                commentChannelDTOList.add(commentChannelDTO);
            }
        }
        return commentChannelDTOList;
    }

    private CommentChannelDTO initCommentChannel(JsonNode item) {
        CommentChannelDTO commentChannelDTO = new CommentChannelDTO();

        JsonNode snippet = item.get("snippet").get("topLevelComment").get("snippet");
        commentChannelDTO.setAuthorDisplayName(snippet.get("authorDisplayName").asText());
        commentChannelDTO.setAuthorChannelId(snippet.get("authorChannelId").get("value").asText());
        commentChannelDTO.setPublishedAt(snippet.get("publishedAt").asText());
        commentChannelDTO.setLikeCount(snippet.get("likeCount").asInt());
        commentChannelDTO.setTextOriginal(snippet.get("textOriginal").asText());
        commentChannelDTO.setTextDisplay(snippet.get("textDisplay").asText());
        return commentChannelDTO;
    }
}
