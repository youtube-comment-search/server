package hello.youtubecommentsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelDTO {
    String kind;

    @Schema(description = "채널 이름")
    String title;
    @Schema(description = "채널 id")
    String channelId;
    @Schema(description = "채널 설명")
    String description;
    @Schema(description = "썸네일 기본 url")
    String thumbnailsDefaultUrl;
    @Schema(description = "썸네일 medium url")
    String thumbnailsMediumUrl;
    @Schema(description = "썸네일 high url")
    String thumbnailsHighUrl;
}