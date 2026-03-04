package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import kr.ac.kopo.ctc.kopo28.board.repository.CommentRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.PostRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Comment> getPageByPostId(Long postId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.ASC, "createdAt", "id"));
        return commentRepository.findByPostId(postId, pageable);
    }

    @Override
    @Transactional
    public Comment save(String content, Long postId, Long parentId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("post not found: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("user not found: " + userId));

        Comment c = new Comment();
        c.setPost(post);             // ★ 없다고 뜨면 Comment 엔티티에 @Setter 추가
        c.setAuthor(user);           // 필드명이 user 라면 setUser(...) 로 바꾸세요
        c.setContent(content);

        if (parentId != null) {
            Comment parent = commentRepository.findById(parentId)
                    .orElseThrow(() -> new NoSuchElementException("parent not found: " + parentId));
            c.setParent(parent);     // ★ 없다고 뜨면 setter 추가
        }

        // createdAt은 @CreationTimestamp로 자동 설정되므로 setCreatedAt(...) 필요 없음
        return commentRepository.save(c);
    }

    @Override
    @Transactional
    public void deleteById(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new NoSuchElementException("comment not found: " + commentId);
        }
        commentRepository.deleteById(commentId); // children은 orphanRemoval=true면 같이 삭제
    }
}
