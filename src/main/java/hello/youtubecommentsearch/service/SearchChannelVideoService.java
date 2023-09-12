package hello.youtubecommentsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import hello.youtubecommentsearch.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.transaction.jta.platform.internal.JOnASJtaPlatform;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Slf4j
@Service
public class SearchChannelVideoService {
    @Value("${youtube-api-key}")
    private String DEVELOPER_KEY;
    private static final String API_URL = "https://youtube.googleapis.com/youtube/v3/search";
    private static final String PART = "snippet";
//    private static final String QUERY = "선바";

    private final WebClient webClient;

    public SearchChannelVideoService() {
        this.webClient = WebClient.create();
    }

    // TODO: 2023/07/20 api 파라미터가 videoId인지 channelId인지 어떻게 구별할지?
    // 쿼리스트링을 주는거니까 상관 없지 않나?

    public Mono<ChannelVideoResponseDTO> search(String query) {
        String url = String.format("%s?part=%s&q=%s&key=%s", API_URL, PART, query, DEVELOPER_KEY);
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode -> parseResponse(jsonNode));
    }

    private ChannelVideoResponseDTO parseResponse(JsonNode jsonNode) {
        ChannelVideoResponseDTO channelVideoResponseDTO = new ChannelVideoResponseDTO();
        if (jsonNode != null && jsonNode.has("items") && jsonNode.get("items").isArray()) {
            for (JsonNode item :jsonNode.get("items")) {
                JsonNode id = item.get("id");
                if (id != null && id.has("kind")) {
                    String kind = id.get("kind").asText();
                    if ("youtube#channel".equals(kind)) {
                        ChannelDTO channelDTO = initChannel(item, id);
                        channelVideoResponseDTO.getChannels().add(channelDTO);
                    } else if ("youtube#video".equals(kind)) {
                        VideoDTO videoDTO = initVideo(item, id);
                        channelVideoResponseDTO.getVideos().add(videoDTO);
                    }
                }
            }
        }
        return channelVideoResponseDTO;
    }

    private ChannelDTO initChannel(JsonNode item, JsonNode id) {
        ChannelDTO channelDTO = new ChannelDTO();

        String kind = id.get("kind").asText();
        String channelId = id.get("channelId").asText();
        JsonNode snippet = item.get("snippet");
        String title = snippet.get("title").asText();
        String description = snippet.get("description").asText();
        JsonNode thumbnails = snippet.get("thumbnails");
        String thumbnailsDefaultUrl = thumbnails.get("default").get("url").asText();
        String thumbnailsMediumUrl = thumbnails.get("medium").get("url").asText();
        String thumbnailsHighUrl = thumbnails.get("high").get("url").asText();

        channelDTO.setKind(kind);
        channelDTO.setChannelId(channelId);
        channelDTO.setTitle(title);
        channelDTO.setDescription(description);
        channelDTO.setThumbnailsDefaultUrl(thumbnailsDefaultUrl);
        channelDTO.setThumbnailsMediumUrl(thumbnailsMediumUrl);
        channelDTO.setThumbnailsHighUrl(thumbnailsHighUrl);

        return channelDTO;
    }

    private VideoDTO initVideo(JsonNode item, JsonNode id) {
        VideoDTO videoDTO = new VideoDTO();

        String kind = id.get("kind").asText();
        String videoId = id.get("videoId").asText();
        JsonNode snippet = item.get("snippet");
        String channelId = snippet.get("channelId").asText();
        String channelTitle = snippet.get("channelTitle").asText();
        String title = snippet.get("title").asText();
        JsonNode thumbnails = snippet.get("thumbnails");
        String thumbnailsDefaultUrl = thumbnails.get("default").get("url").asText();
        String thumbnailsMediumUrl = thumbnails.get("medium").get("url").asText();
        String thumbnailsHighUrl = thumbnails.get("high").get("url").asText();
        int thumbnailsDefaultWidth = thumbnails.get("default").get("width").asInt();
        int thumbnailsDefaultHeight = thumbnails.get("default").get("height").asInt();
        int thumbnailsMediumWidth = thumbnails.get("medium").get("width").asInt();
        int thumbnailsMediumHeight = thumbnails.get("medium").get("height").asInt();
        int thumbnailsHighWidth = thumbnails.get("high").get("width").asInt();
        int thumbnailsHighHeight = thumbnails.get("high").get("height").asInt();

        videoDTO.setKind(kind);
        videoDTO.setVideoId(videoId);
        videoDTO.setTitle(title);
        videoDTO.setChannelId(channelId);
        videoDTO.setChannelTitle(channelTitle);
        videoDTO.setThumbnailsDefaultUrl(thumbnailsDefaultUrl);
        videoDTO.setThumbnailsMediumUrl(thumbnailsMediumUrl);
        videoDTO.setThumbnailsHighUrl(thumbnailsHighUrl);

        videoDTO.setThumbnailsDefaultWidth(thumbnailsDefaultWidth);
        videoDTO.setThumbnailsDefaultHeight(thumbnailsDefaultHeight);
        videoDTO.setThumbnailsMediumWidth(thumbnailsMediumWidth);
        videoDTO.setThumbnailsDefaultHeight(thumbnailsMediumHeight);
        videoDTO.setThumbnailsHighWidth(thumbnailsHighWidth);
        videoDTO.setThumbnailsHighHeight(thumbnailsHighHeight);
        return videoDTO;
    }
}
