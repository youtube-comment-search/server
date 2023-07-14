package hello.youtubecommentsearch.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Video {
    String videoId;
    String title;

    String channelId;
    String channelTitle;

    String thumbnailsDefaultUrl;
    int thumbnailsDefaultWidth;
    int thumbnailsDefaultHeight;

    String thumbnailsMediumUrl;
    int thumbnailsMediumWidth;
    int thumbnailsMediumHeight;

    String thumbnailsHighUrl;
    int thumbnailsHighWidth;
    int thumbnailsHighHeight;
}
