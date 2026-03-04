package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import org.springframework.data.domain.Page;

public interface CommentService {

    Page<Comment> getPageByPostId(Long postId, int page, int size);

    // 로그인 사용자 id를 전달하는 형태 (Principal 쓰면 이 파라미터는 빼도 됩니다)
    Comment save(String content, Long postId, Long parentId, Long userId);

    void deleteById(Long commentId);
}
