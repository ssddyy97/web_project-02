package kr.ac.kopo.ctc.kopo28.board.service;

import kr.ac.kopo.ctc.kopo28.board.config.JwtTokenProvider;
import kr.ac.kopo.ctc.kopo28.board.domain.User;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthRequest;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthResponse;
import kr.ac.kopo.ctc.kopo28.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtTokenService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /** 로그인 → 인증 컨텍스트 설정 → JWT 발급 */
    public AuthResponse authenticateUser(AuthRequest authRequest) {
        // 1) 사용자 인증
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        );

        // 2) 인증 성공 시 SecurityContext에 저장 (중요!)
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 3) JWT 생성 (generateToken(Authentication) 시그니처 유지)
        String jwt = jwtTokenProvider.generateToken(authentication);

        return new AuthResponse(jwt, "Bearer", null);
    }

    /** 현재 요청 스레드에 인증이 실려있는지 여부 */
    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication.getClass().getName()
                .contains("AnonymousAuthenticationToken"));
    }

    /** 회원가입 */
    public String registerUser(AuthRequest authRequest) {
        if (userRepository.findByName(authRequest.getUsername()).isPresent()) {
            return "Username already exists!";
        }
        User newUser = new User();
        newUser.setName(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        newUser.setRole("USER");
        userRepository.save(newUser);
        return "User registered successfully!";
    }
}

