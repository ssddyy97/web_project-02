package kr.ac.kopo.ctc.kopo28.board.repository;
import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 페이징 조회 (post.id 로 탐색)
    @Query("SELECT c FROM Comment c WHERE c.post.post_id = :postId")
    Page<Comment> findByPostId(@Param("postId") Long postId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.post.post_id = :postId AND c.parent IS NULL ORDER BY c.createdAt ASC")
    List<Comment> findByPostIdAndParentIsNullOrderByCreatedAtAsc(@Param("postId") Long postId);

    // 어떤 댓글의 자식들
    List<Comment> findByParent_IdOrderByCreatedAtAsc(Long parentId);
}