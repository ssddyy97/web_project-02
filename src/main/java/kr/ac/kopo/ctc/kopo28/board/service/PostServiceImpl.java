package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Board;
import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.repository.BoardRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, BoardRepository boardRepository) {
        this.postRepository = postRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public Page<Post> findAllByBoardId(Long boardId, Pageable pageable) {
        return postRepository.findByBoardId(boardId, pageable);
    }

    @Transactional
    @Override
    public Post findById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
        // 조회수 증가
        post.setView(post.getView() + 1);
        return postRepository.save(post);
    }

    @Override
    public Post save(Post post) {
        Long boardId = post.getBoard().getBoard_id();
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NoSuchElementException("Board not found with id: " + boardId));
        post.setBoard(board);
        return postRepository.save(post);
    }

    @Transactional
    @Override
    public Post update(Long postId, Post postDetails) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));

        post.setTitle(postDetails.getTitle());
        post.setBody(postDetails.getBody());

        return postRepository.save(post);
    }

    @Override
    public void deleteById(Long postId) {
        postRepository.deleteById(postId);
    }

    @Transactional
    @Override
    public Post recommend(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("Post not found with id: " + postId));
        post.setLikes(post.getLikes() + 1);
        return postRepository.save(post);
    }

    @Override
    public Page<Post> searchPosts(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingOrBodyContaining(keyword, keyword, pageable);
    }
}