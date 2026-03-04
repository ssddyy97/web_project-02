package kr.ac.kopo.ctc.kopo28.board;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthRequest;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthResponse;
import kr.ac.kopo.ctc.kopo28.board.service.JwtTokenService;
import kr.ac.kopo.ctc.kopo28.board.web.AuthController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    JwtTokenService jwtTokenService;

    MockMvc mockMvc;
    ObjectMapper om = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(new AuthController(jwtTokenService))
                .build();
    }

    @Test
    void authenticate_returns_token() throws Exception {
        given(jwtTokenService.authenticateUser(any()))
                .willReturn(new AuthResponse("tok123", "Bearer", null));

        var req = new AuthRequest();
        req.setUsername("admin");
        req.setPassword("admin");

        mockMvc.perform(post("/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value("tok123"))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }
}
