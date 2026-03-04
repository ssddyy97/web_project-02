package kr.ac.kopo.ctc.kopo28.board.web;

import kr.ac.kopo.ctc.kopo28.board.dto.AuthRequest;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthResponse;
import kr.ac.kopo.ctc.kopo28.board.service.JwtTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// kr.ac.kopo.ctc.kopo28.board.web.AuthController
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtTokenService jwtTokenService; // ★ final + @RequiredArgsConstructor

    @PostMapping(value="/authenticate", consumes= MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody AuthRequest req){
        AuthResponse authResponse = jwtTokenService.authenticateUser(req);
        return ResponseEntity.ok(authResponse);
    }
}
