package hello.youtubecommentsearch.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentChannelDTO {
    @Schema(description = "댓글 작성자 이름")
    String authorDisplayName;
    @Schema(description = "댓글 작성자 채널 id")
    String authorChannelId;
//    Date publishedAt;
    @Schema(description = "작성 날짜 시간(Date로 자료형 변경 예정)")
    String publishedAt;
    @Schema(description = "댓글 좋아요 수")
    int likeCount;
    @Schema(description = "댓글 원본")
    String textOriginal;
    @Schema(description = "display 댓글(textOriginal과 차이를 모르겠어서 일단 둘다 넣어둠)")
    String textDisplay;
}
