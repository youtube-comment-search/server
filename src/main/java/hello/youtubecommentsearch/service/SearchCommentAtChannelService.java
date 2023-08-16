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
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
    private static final int MAXRESULTS = 100;
    private final WebClient webClient;


    public SearchCommentAtChannelService() {
        this.webClient = WebClient.create();
    }

    public Mono<List<CommentChannelDTO>> getAllComments(String channelId) {
        List<CommentChannelDTO> allComments = new ArrayList<>();
        String nextPageToken = null;

        return getComments(channelId, nextPageToken, allComments);
    }

    private Mono<List<CommentChannelDTO>> getComments(String channelId, String nextPageToken, List<CommentChannelDTO> allComments) {
        String url = String.format("%s?part=%s&allThreadsRelatedToChannelId=%s&maxResults=%s&key=%s",
                API_URL, PART, channelId, MAXRESULTS, DEVELOPER_KEY);

        if (nextPageToken != null) {
            url += "&pageToken=" + nextPageToken;
        }

        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatus::is5xxServerError, this::handleServerError)
                .bodyToMono(JsonNode.class)
                .flatMap(jsonNode -> {
                    List<CommentChannelDTO> commentChannelDTOList = parseResponse(jsonNode);
                    allComments.addAll(commentChannelDTOList);

                    // 다음페이지 있는지 토큰으로 확인
                    String nextPageTokenResponse = jsonNode.get("nextPageToken") != null ? jsonNode.get("nextPageToken").asText() : null;
                    if (nextPageTokenResponse != null) {
                        // 재귀적으로 다음페이지 가져옴
                        return getComments(channelId, nextPageTokenResponse, allComments);
                    } else {
                        // 다 가져오고 맨 마지막 페이지일 때 return
                        return Mono.just(allComments);
                    }
                })
                .onErrorResume(this::handleError);
    }

    public Mono<List<CommentChannelDTO>> search(String channelId){
        String url = String.format("%s?part=%s&allThreadsRelatedToChannelId=%s&maxResults=%s&key=%s", API_URL, PART, channelId, MAXRESULTS, DEVELOPER_KEY);
        return webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
                .onStatus(HttpStatus::is5xxServerError, this::handleServerError)
                .bodyToMono(JsonNode.class)
                .map(this::parseResponse)
                .onErrorResume(this::handleError);

    }

    private List<CommentChannelDTO> parseResponse(JsonNode jsonNode) {
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

    private Mono<List<CommentChannelDTO>> handleError(Throwable throwable) {
        // Handle other errors here, if needed
        return Mono.error(throwable);
    }
}
