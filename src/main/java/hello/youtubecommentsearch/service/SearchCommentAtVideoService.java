package hello.youtubecommentsearch.service;

import hello.youtubecommentsearch.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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

    public Mono<List<CommentVideoDTO>> search(String videoId) {
        String url = String.format("%s?part=%s&videoId=%s&key=%s", API_URL, PART, videoId, DEVELOPER_KEY);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatus::is5xxServerError, this::handleServerError)
                .bodyToMono(JsonNode.class)
                .map(this::parseResponse)
                .onErrorResume(this::handleError);
    }

    private List<CommentVideoDTO> parseResponse(JsonNode jsonNode) {
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

    private Mono<? extends Throwable> handleClientError(ClientResponse response) {
        // Handle 4xx Client Errors here, if needed
        return Mono.error(new WebClientResponseException(response.statusCode().value(),
                "Client Error", response.headers().asHttpHeaders(), null, null));
    }

    private Mono<? extends Throwable> handleServerError(ClientResponse response) {
        // Handle 5xx Server Errors here, if needed
        return Mono.error(new WebClientResponseException(response.statusCode().value(),
                "Server Error", response.headers().asHttpHeaders(), null, null));
    }

    private Mono<List<CommentVideoDTO>> handleError(Throwable throwable) {
        // Handle other errors here, if needed
        return Mono.error(throwable);
    }
}
