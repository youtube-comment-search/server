package hello.youtubecommentsearch.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


public class ChannelVideoResponseDTO {
    @ApiModelProperty(notes = "채널 리스트")
    private final List<ChannelDTO> channelDTOS = new ArrayList<>();
    @ApiModelProperty(notes = "영상 리스트")
    private final List<VideoDTO> videoDTOS = new ArrayList<>();
    @ApiModelProperty(notes = "에러 메시지")
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
