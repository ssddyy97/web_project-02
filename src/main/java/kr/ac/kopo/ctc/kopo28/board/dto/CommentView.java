package kr.ac.kopo.ctc.kopo28.board.dto;

import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CommentView {
    Long id;
    Long parentId;
    int depth;
    String content;
    String authorName;
    LocalDateTime createdAt;

    public static CommentView of(Comment c) {
        return CommentView.builder()
                .id(c.getId())
                .parentId(c.getParent() == null ? null : c.getParent().getId())
                .depth(calcDepth(c))                                   // ★ 부모 체인으로 깊이 계산
                .content(isDeleted(c) ? "(삭제된 댓글입니다)" : c.getContent())
                .authorName(c.getAuthor().getName())
                .createdAt(c.getCreatedAt())
                .build();
    }

    /** 부모를 타고 올라가며 깊이 계산 */
    private static int calcDepth(Comment c) {
        int d = 0;
        Comment cur = c;
        while (cur.getParent() != null) {
            d++;
            cur = cur.getParent();
        }
        return d;
    }

    /** soft delete 필드가 없으면 임시 기준: 내용이 null/빈값이면 삭제로 간주 */
    private static boolean isDeleted(Comment c) {
        return c.getContent() == null || c.getContent().isBlank();
        // 만약 엔티티에 boolean deleted 가 있으면 여기서 return c.isDeleted(); 로 바꾸세요.
    }
}

