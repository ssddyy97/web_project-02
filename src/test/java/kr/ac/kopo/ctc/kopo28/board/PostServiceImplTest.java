package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.repository.PostRepository;
import kr.ac.kopo.ctc.kopo28.board.service.PostServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Test
    @DisplayName("findById: 조회수 1 증가 후 Post 반환")
    void findById_ok() {
        // given
        Post p = new Post();
        p.setPost_id(10L);
        p.setView(7);
        given(postRepository.findById(10L)).willReturn(Optional.of(p));
        given(postRepository.save(any(Post.class))).willAnswer(inv -> inv.getArgument(0));

        // when
        Post result = postService.findById(10L);

        // then
        assertThat(result.getView()).isEqualTo(8);
        then(postRepository).should().findById(10L);
        then(postRepository).should().save(p);
    }

    @Test
    @DisplayName("findById: 존재하지 않으면 예외")
    void findById_notFound() {
        // given
        given(postRepository.findById(99L)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> postService.findById(99L))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("findAllByBoardId: 페이징된 목록 반환")
    void findAllByBoardId_ok() {
        // given
        Post p1 = new Post(); p1.setPost_id(1L);
        Post p2 = new Post(); p2.setPost_id(2L);
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> page = new PageImpl<>(List.of(p1, p2), pageable, 2);
        given(postRepository.findByBoardId(anyLong(), any(Pageable.class))).willReturn(page);

        // when
        Page<Post> result = postService.findAllByBoardId(1L, pageable);

        // then
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent()).extracting(Post::getPost_id).containsExactly(1L, 2L);
    }
}