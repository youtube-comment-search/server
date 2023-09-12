package hello.youtubecommentsearch.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"channelDTOS", "videoDTOS"})
public class ChannelVideoResponseDTO {
    @Schema(description = "채널 리스트")
    private final List<ChannelDTO> channelDTOS = new ArrayList<>();
    @Schema(description = "영상 리스트")
    private final List<VideoDTO> videoDTOS = new ArrayList<>();

    public ChannelVideoResponseDTO() {
    }

    public List<ChannelDTO> getChannels() {
        return channelDTOS;
    }

    public List<VideoDTO> getVideos() {
        return videoDTOS;
    }
}
