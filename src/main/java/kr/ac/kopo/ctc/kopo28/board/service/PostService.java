package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    /**
     * 특정 게시판의 게시글 목록을 페이징하여 조회
     * @param boardId 게시판 ID
     * @param pageable 페이징 정보
     * @return 페이징된 게시글 목록
     */
    Page<Post> findAllByBoardId(Long boardId, Pageable pageable);

    /**
     * 게시글 상세 조회. 조회 시 조회수 1 증가
     * @param postId 게시글 ID
     * @return 조회된 게시글
     */
    Post findById(Long postId);

    /**
     * 게시글 저장
     * @param post 저장할 게시글
     * @return 저장된 게시글
     */
    Post save(Post post);

    /**
     * 게시글 수정
     * @param postId 수정할 게시글 ID
     * @param postDetails 수정할 내용이 담긴 객체
     * @return 수정된 게시글
     */
    Post update(Long postId, Post postDetails);

    /**
     * 게시글 삭제
     * @param postId 삭제할 게시글 ID
     */
    void deleteById(Long postId);

    /**
     * 게시글 추천. 추천수 1 증가
     * @param postId 추천할 게시글 ID
     * @return 추천 후의 게시글
     */
    Post recommend(Long postId);

    /**
     * 게시글 검색 (제목 또는 내용)
     * @param keyword 검색 키워드
     * @param pageable 페이징 정보
     * @return 검색된 게시글 목록
     */
    Page<Post> searchPosts(String keyword, Pageable pageable);
}