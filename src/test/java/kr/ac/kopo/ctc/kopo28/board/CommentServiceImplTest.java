package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.domain.Comment;
import kr.ac.kopo.ctc.kopo28.board.domain.Post;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import kr.ac.kopo.ctc.kopo28.board.repository.CommentRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.PostRepository;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import kr.ac.kopo.ctc.kopo28.board.service.CommentService;
import kr.ac.kopo.ctc.kopo28.board.service.CommentServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceImplTest {

    @Mock
    CommentRepository commentRepository;
    @Mock
    PostRepository postRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = mockUser(1L, "user");
        var auth = new UsernamePasswordAuthenticationToken(
                "user", "N/A", List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        lenient().when(userRepository.findByName("user"))
                .thenReturn(Optional.of(mockUser));
        lenient().when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("save: 최상위 댓글 저장 시 Post/User/createdAt 설정")
    void save_topLevel() {
        given(postRepository.findById(1L)).willReturn(Optional.of(mockPost(1L)));
        given(commentRepository.save(Mockito.any(Comment.class)))
                .willAnswer(inv -> {
                    Comment c = inv.getArgument(0);
                    c.setId(100L); // Simulate saving by setting an ID
                    c.setCreatedAt(LocalDateTime.now());
                    return c;
                });

        Comment saved = commentService.save("hello", 1L, null, 1L);

        assertThat(saved.getPost()).isNotNull();
        assertThat(saved.getAuthor()).isNotNull();
        assertThat(saved.getContent()).isEqualTo("hello");
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("save: 대댓글 저장 시 parentComment 연결")
    void save_reply() {
        given(postRepository.findById(1L)).willReturn(Optional.of(mockPost(1L)));
        Comment parent = new Comment();
        parent.setId(7L);
        given(commentRepository.findById(7L)).willReturn(Optional.of(parent));
        given(commentRepository.save(Mockito.any(Comment.class)))
                .willAnswer(inv -> inv.getArgument(0));

        Comment saved = commentService.save("reply content", 1L, 7L, 1L);

        assertThat(saved.getParent()).isNotNull();
        assertThat(saved.getParent().getId()).isEqualTo(7L);
    }

    @Test
    @DisplayName("getPageByPostId: 목록 반환")
    void getPageByPostId() {
        Comment c = new Comment();
        c.setId(1L);
        Page<Comment> page = new PageImpl<>(List.of(c));
        given(commentRepository.findByPostId(Mockito.eq(9L), Mockito.any(Pageable.class)))
                .willReturn(page);

        assertThat(commentService.getPageByPostId(9L, 0, 10).getContent()).hasSize(1);
    }

    @Test
    @DisplayName("deleteById: 미존재 시 예외")
    void deleteById_notFound() {
        given(commentRepository.existsById(999L)).willReturn(false);
        assertThatThrownBy(() -> commentService.deleteById(999L))
                .isInstanceOf(NoSuchElementException.class);
    }

    // ---- helpers ----
    private static Post mockPost(Long id) {
        Post p = new Post();
        p.setPost_id(id);
        p.setView(0);
        p.setCreatedAt(LocalDateTime.now());
        return p;
    }

    private static User mockUser(Long id, String name) {
        User u = new User();
        u.setUser_id(id);
        u.setName(name);
        return u;
    }
}