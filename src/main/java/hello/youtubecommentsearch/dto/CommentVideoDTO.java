package hello.youtubecommentsearch.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentVideoDTO {
    //videoId, channelId 포함 필요?
    String authorDisplayName;
    String authorChannelId;
//    Date publishedAt;
    String publishedAt;
    int likeCount;
    String textOriginal;
    String textDisplay;
}
