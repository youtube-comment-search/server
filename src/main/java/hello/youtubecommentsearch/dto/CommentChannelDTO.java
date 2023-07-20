package hello.youtubecommentsearch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentChannelDTO {
    String authorDisplayName;
    String authorChannelId;
//    Date publishedAt;
    String publishedAt;
    int likeCount;
    String textOriginal;
    String textDisplay;
}
