package kr.ac.kopo.ctc.kopo28.board;

import kr.ac.kopo.ctc.kopo28.board.web.HomeController;
import kr.ac.kopo.ctc.kopo28.board.service.JwtTokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class HomeControllerUnitTest {

    JwtTokenService jwtTokenService = Mockito.mock(JwtTokenService.class);
    HomeController controller = new HomeController(jwtTokenService);

    @Test
    void root_returns_home_view() {
        String view = controller.root();
        assertThat(view).isEqualTo("home");
    }

    @Test
    void login_returns_login_view() {
        String view = controller.login();
        assertThat(view).isEqualTo("login");
    }
}