package hello.youtubecommentsearch.service;

import com.fasterxml.jackson.databind.JsonNode;
import hello.youtubecommentsearch.dto.CommentDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchCommentService {
    @Value("${youtube-api-key}")
    private String DEVELOPER_KEY;
    private static final String API_URL = "https://youtube.googleapis.com/youtube/v3/commentThreads";
    private static final String PART = "snippet, replies";
    //    private String channelId = "UCrpoE9e2-eWcj8AYvwYdebw";
    private static final int MAXRESULTS = 20;
    private final WebClient webClient;


    public SearchCommentService() {
        this.webClient = WebClient.create();
    }

//    public Mono<List<CommentDTO>> getAllComments(String channelId) {
//        List<CommentDTO> allComments = new ArrayList<>();
//        String nextPageToken = null;
//
//        return getComments(channelId, nextPageToken, allComments);
//    }
//
//    private Mono<List<CommentDTO>> getComments(String channelId, String nextPageToken, List<CommentDTO> allComments) {
//        String url = String.format("%s?part=%s&allThreadsRelatedToChannelId=%s&maxResults=%s&key=%s",
//                API_URL, PART, channelId, MAXRESULTS, DEVELOPER_KEY);
//
//        if (nextPageToken != null) {
//            url += "&pageToken=" + nextPageToken;
//        }
//
//        return webClient.get()
//                .uri(url)
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
//                .onStatus(HttpStatus::is5xxServerError, this::handleServerError)
//                .bodyToMono(JsonNode.class)
//                .flatMap(jsonNode -> {
//                    List<CommentDTO> CommentDTOList = parseResponse(jsonNode);
//                    allComments.addAll(CommentDTOList);
//
//                    // 다음페이지 있는지 토큰으로 확인
//                    String nextPageTokenResponse = jsonNode.get("nextPageToken") != null ? jsonNode.get("nextPageToken").asText() : null;
//                    if (nextPageTokenResponse != null) {
//                        // 재귀적으로 다음페이지 가져옴
//                        return getComments(channelId, nextPageTokenResponse, allComments);
//                    } else {
//                        // 다 가져오고 맨 마지막 페이지일 때 return
//                        return Mono.just(allComments);
//                    }
//                })
//                .onErrorResume(this::handleError);
//    }

    public Mono<List<CommentDTO>> search(String id, String type, String includeKeyword, String excludeKeyword){
        String url = String.format("%s?part=%s&allThreadsRelatedToChannelId=%s&maxResults=%s&key=%s", API_URL, PART, id, MAXRESULTS, DEVELOPER_KEY);
        return webClient.get()
                .uri(url)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, this::handleClientError)
//                .onStatus(HttpStatus::is5xxServerError, this::handleServerError)
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> parseResponse(jsonNode, includeKeyword, excludeKeyword));
//                .onErrorResume(this::handleError);
//                .onErrorMap(WebClientResponseException.class, ex -> {
//                    HttpStatus statusCode = ex.getStatusCode();
//                    String responseBody = ex.getResponseBodyAsString();
//                    return new ResponseStatusException(statusCode, responseBody);
//                });

    }

    private List<CommentDTO> parseResponse(JsonNode jsonNode, String includeKeyword, String excludeKeyword) {
        List<CommentDTO> CommentDTOList = new ArrayList<>();
        if (jsonNode != null && jsonNode.has("items") && jsonNode.get("items").isArray()) {
            for (JsonNode item : jsonNode.get("items")) {
                CommentDTO CommentDTO = initCommentChannel(item);
                CommentDTOList.add(CommentDTO);
            }
        }
        return CommentDTOList.stream()
                .filter(comment -> includeKeyword == null || comment.getTextOriginal().contains(includeKeyword))
                .filter(comment -> excludeKeyword == null || !comment.getTextOriginal().contains(excludeKeyword))
                .collect(Collectors.toList());
    }

    private CommentDTO initCommentChannel(JsonNode item) {
        CommentDTO CommentDTO = new CommentDTO();

        JsonNode snippet = item.get("snippet").get("topLevelComment").get("snippet");
        CommentDTO.setAuthorDisplayName(snippet.get("authorDisplayName").asText());
        CommentDTO.setAuthorChannelId(snippet.get("authorChannelId").get("value").asText());
        CommentDTO.setPublishedAt(snippet.get("publishedAt").asText());
        CommentDTO.setLikeCount(snippet.get("likeCount").asInt());
        CommentDTO.setTextOriginal(snippet.get("textOriginal").asText());
        CommentDTO.setTextDisplay(snippet.get("textDisplay").asText());
        return CommentDTO;
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

    private Mono<List<CommentDTO>> handleError(Throwable throwable) {
        // Handle other errors here, if needed
        return Mono.error(throwable);
    }
}
