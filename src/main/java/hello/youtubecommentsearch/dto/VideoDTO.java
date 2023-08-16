package hello.youtubecommentsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoDTO {
    String kind;

    @Schema(description = "영상 id")
    String videoId;
    @Schema(description = "영상 제목")
    String title;
    @Schema(description = "채널 id")
    String channelId;
    @Schema(description = "채널 이름")
    String channelTitle;

    @Schema(description = "썸네일 기본 url")
    String thumbnailsDefaultUrl;
    @Schema(description = "썸네일 기본 width")
    int thumbnailsDefaultWidth;
    @Schema(description = "썸네일 기본 height")
    int thumbnailsDefaultHeight;

    @Schema(description = "썸네일 medium url")
    String thumbnailsMediumUrl;
    @Schema(description = "썸네일 medium width")
    int thumbnailsMediumWidth;
    @Schema(description = "썸네일 medium height")
    int thumbnailsMediumHeight;

    @Schema(description = "썸네일 high url")
    String thumbnailsHighUrl;
    @Schema(description = "썸네일 high width")
    int thumbnailsHighWidth;
    @Schema(description = "썸네일 high height")
    int thumbnailsHighHeight;
}
