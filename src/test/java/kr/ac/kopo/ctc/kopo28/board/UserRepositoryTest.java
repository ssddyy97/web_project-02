package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.BoardApplication;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = BoardApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("페이지네이션으로 전체 사용자 조회")
    void findAllPaged() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<User> page = userRepository.findAll(pageRequest);

        page.forEach(user -> System.out.println(user.getName()));

        assertThat(page.getContent()).isNotEmpty();
    }
}
