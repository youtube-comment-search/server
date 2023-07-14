package hello.youtubecommentsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Channel {
    String title;
    String channelId;
    String description;
    String thumbnailsDefaultUrl;
    String thumbnailsMediumUrl;
    String thumbnailsHighUrl; // defalut, medium, high 각각 값 모두 다름
}
