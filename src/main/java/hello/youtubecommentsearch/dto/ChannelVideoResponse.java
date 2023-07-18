package hello.youtubecommentsearch.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;


public class ChannelVideoResponse {
    @ApiModelProperty(notes = "채널 리스트")
    private final List<Channel> channels = new ArrayList<>();
    @ApiModelProperty(notes = "영상 리스트")
    private final List<Video> videos = new ArrayList<>();
    @ApiModelProperty(notes = "에러 메시지")
    private String errorMessage;

    public ChannelVideoResponse() {
    }
    public ChannelVideoResponse(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public List<Channel> getChannels() {
        return channels;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public String getErrorMessage() { return errorMessage;}
}
