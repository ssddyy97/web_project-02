package kr.ac.kopo.ctc.kopo28.board.web;

import kr.ac.kopo.ctc.kopo28.board.service.JwtTokenService;
import kr.ac.kopo.ctc.kopo28.board.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final JwtTokenService jwtTokenService;

    @GetMapping("/")
    public String root() { return "home"; }

    @GetMapping("/login")
    public String login() { return "login"; } // ★ 무조건 JSP 반환

    @GetMapping("/register")
    public String showRegistrationForm() { return "register"; }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute AuthRequest authRequest, Model model) {
        String result = jwtTokenService.registerUser(authRequest);
        if (result.equals("User registered successfully!")) {
            model.addAttribute("successMessage", result);
            return "login"; // Redirect to login page on success
        } else {
            model.addAttribute("errorMessage", result);
            return "register"; // Stay on registration page with error
        }
    }
}
