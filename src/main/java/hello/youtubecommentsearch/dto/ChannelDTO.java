package hello.youtubecommentsearch.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDTO {
    String kind;

    @ApiModelProperty(notes = "채널 이름")
    String title;
    @ApiModelProperty(notes = "채널 id")
    String channelId;
    @ApiModelProperty(notes = "채널 설명")
    String description;
    @ApiModelProperty(notes = "썸네일 기본 url")
    String thumbnailsDefaultUrl;
    @ApiModelProperty(notes = "썸네일 medium url")
    String thumbnailsMediumUrl;
    @ApiModelProperty(notes = "썸네일 high url")
    String thumbnailsHighUrl;
}