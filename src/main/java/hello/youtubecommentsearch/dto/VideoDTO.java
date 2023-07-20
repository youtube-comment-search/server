package hello.youtubecommentsearch.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
    String kind;

    @ApiModelProperty(notes = "영상 id")
    String videoId;
    @ApiModelProperty(notes = "영상 제목")
    String title;
    @ApiModelProperty(notes = "채널 id")
    String channelId;
    @ApiModelProperty(notes = "채널 이름")
    String channelTitle;

    @ApiModelProperty(notes = "썸네일 기본 url")
    String thumbnailsDefaultUrl;
    @ApiModelProperty(notes = "썸네일 기본 width")
    int thumbnailsDefaultWidth;
    @ApiModelProperty(notes = "썸네일 기본 height")
    int thumbnailsDefaultHeight;

    @ApiModelProperty(notes = "썸네일 medium url")
    String thumbnailsMediumUrl;
    @ApiModelProperty(notes = "썸네일 medium width")
    int thumbnailsMediumWidth;
    @ApiModelProperty(notes = "썸네일 medium height")
    int thumbnailsMediumHeight;

    @ApiModelProperty(notes = "썸네일 high url")
    String thumbnailsHighUrl;
    @ApiModelProperty(notes = "썸네일 high width")
    int thumbnailsHighWidth;
    @ApiModelProperty(notes = "썸네일 high height")
    int thumbnailsHighHeight;
}
