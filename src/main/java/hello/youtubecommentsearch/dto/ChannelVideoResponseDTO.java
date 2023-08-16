package hello.youtubecommentsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;


public class ChannelVideoResponseDTO {
    @Schema(description = "채널 리스트")
    private final List<ChannelDTO> channelDTOS = new ArrayList<>();
    @Schema(description = "영상 리스트")
    private final List<VideoDTO> videoDTOS = new ArrayList<>();
    @Schema(description = "에러 메시지")
    private String errorMessage;

    public ChannelVideoResponseDTO() {
    }
    public ChannelVideoResponseDTO(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public List<ChannelDTO> getChannels() {
        return channelDTOS;
    }

    public List<VideoDTO> getVideos() {
        return videoDTOS;
    }

    public String getErrorMessage() { return errorMessage;}
}
