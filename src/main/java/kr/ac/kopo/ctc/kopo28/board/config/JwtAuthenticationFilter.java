package kr.ac.kopo.ctc.kopo28.board.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        String uri  = req.getRequestURI();
        String path = req.getServletPath(); // 컨텍스트 경로 있어도 안전
        boolean skip =
                "/".equals(uri) || "/".equals(path) ||
                        "/login".equals(uri) || "/login".equals(path) ||
                        uri.startsWith("/auth/") || path.startsWith("/auth/") ||
                        uri.startsWith("/css/")  || path.startsWith("/css/")  ||
                        uri.startsWith("/js/")   || path.startsWith("/js/")   ||
                        uri.startsWith("/images/") || path.startsWith("/images/") ||
                        "/favicon.ico".equals(uri);
        if (skip) log.debug("[JWT] SKIP {}", uri);
        return skip;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        try {
            String token = resolveToken(req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
                log.debug("[JWT] OK user={} uri={}", auth.getName(), req.getRequestURI());
            } else {
                log.debug("[JWT] no/invalid token uri={}", req.getRequestURI());
            }
        } catch (Exception e) {
            // ★ 여기서 절대 redirect/sendError 하지 말 것 ★
            log.warn("[JWT] filter error on {} : {}", req.getRequestURI(), e.getMessage());
        }

        chain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        String h = req.getHeader("Authorization");
        return (h != null && h.startsWith("Bearer ")) ? h.substring(7) : null;
    }
}
