package hello.youtubecommentsearch.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import hello.youtubecommentsearch.dto.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
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
    private static final String QUERY = "선바";

    private final WebClient webClient;

    public SearchChannelVideoService() {
        this.webClient = WebClient.create();
    }

    public ChannelVideoResponse search() throws JsonProcessingException {
        String url = String.format("%s?part=%s&q=%s&key=%s", API_URL, PART, QUERY, DEVELOPER_KEY);
        Mono<JsonNode> response = webClient.get()
                .uri(url)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> ...)
//		        .onStatus(HttpStatus::is5xxServerError, response -> ...)
                .bodyToMono(JsonNode.class);
        JsonNode jsonNode = response.block();
        ChannelVideoResponse channelVideoResponse = new ChannelVideoResponse();
        if (jsonNode != null && jsonNode.has("items") && jsonNode.get("items").isArray()) {
            for (JsonNode item :jsonNode.get("items")) {
                JsonNode id = item.get("id");
                if (id != null && id.has("kind")) {
                    String kind = id.get("kind").asText();
                    if ("youtube#channel".equals(kind)) {
                        Channel channel = initChannel(item, id);
                        channelVideoResponse.getChannels().add(channel);
                    } else if ("youtube#video".equals(kind)) {
                        Video video = initVideo(item, id);
                        channelVideoResponse.getVideos().add(video);
                    }
                }
            }
        }
        return channelVideoResponse;
    }

    private Channel initChannel(JsonNode item, JsonNode id) {
        Channel channel = new Channel();

        String kind = id.get("kind").asText();
        String channelId = id.get("channelId").asText();
        JsonNode snippet = item.get("snippet");
        String title = snippet.get("title").asText();
        String description = snippet.get("description").asText();
        JsonNode thumbnails = snippet.get("thumbnails");
        String thumbnailsDefaultUrl = thumbnails.get("default").get("url").asText();
        String thumbnailsMediumUrl = thumbnails.get("medium").get("url").asText();
        String thumbnailsHighUrl = thumbnails.get("high").get("url").asText();

        channel.setKind(kind);
        channel.setChannelId(channelId);
        channel.setTitle(title);
        channel.setDescription(description);
        channel.setThumbnailsDefaultUrl(thumbnailsDefaultUrl);
        channel.setThumbnailsMediumUrl(thumbnailsMediumUrl);
        channel.setThumbnailsHighUrl(thumbnailsHighUrl);

        return channel;
    }

    private Video initVideo(JsonNode item, JsonNode id) {
        Video video = new Video();

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

        video.setKind(kind);
        video.setVideoId(videoId);
        video.setTitle(title);
        video.setChannelId(channelId);
        video.setChannelTitle(channelTitle);
        video.setThumbnailsDefaultUrl(thumbnailsDefaultUrl);
        video.setThumbnailsMediumUrl(thumbnailsMediumUrl);
        video.setThumbnailsHighUrl(thumbnailsHighUrl);

        video.setThumbnailsDefaultWidth(thumbnailsDefaultWidth);
        video.setThumbnailsDefaultHeight(thumbnailsDefaultHeight);
        video.setThumbnailsMediumWidth(thumbnailsMediumWidth);
        video.setThumbnailsDefaultHeight(thumbnailsMediumHeight);
        video.setThumbnailsHighWidth(thumbnailsHighWidth);
        video.setThumbnailsHighHeight(thumbnailsHighHeight);
        return video;
    }
}
