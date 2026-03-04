package kr.ac.kopo.ctc.kopo28.board.repository;

import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    @Query("select p from Post p where p.board.board_id = :boardId")
    Page<Post> findByBoardId(@Param("boardId") Long boardId, Pageable pageable);

    // 제목/내용 간단 검색이 필요하면 이렇게 추가(선택)
    @Query("select p from Post p where p.title like %:t% or p.body like %:c% order by p.post_id desc")
    Page<Post> findByTitleContainingOrBodyContaining(@Param("t") String t, @Param("c") String c, Pageable pageable);
}
