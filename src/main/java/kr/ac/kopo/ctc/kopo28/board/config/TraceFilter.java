package kr.ac.kopo.ctc.kopo28.board.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(1) // 보안 필터보다 먼저
public class TraceFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TraceFilter.class);

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String u = request.getRequestURI();
        return u.startsWith("/css/") || u.startsWith("/js/") || u.startsWith("/images/")
                || u.startsWith("/webjars/") || "/favicon.ico".equals(u);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws ServletException, IOException {

        long t0 = System.currentTimeMillis();
        String uri = req.getRequestURI();
        String qs  = req.getQueryString();
        String path = (qs == null ? uri : uri + "?" + qs);
        String user = (req.getUserPrincipal() == null ? null : req.getUserPrincipal().getName());
        String sess = req.getRequestedSessionId();

        log.info("[REQ] {} {} session={} user={}", req.getMethod(), path, sess, user);
        chain.doFilter(req, res);
        String loc = res.getHeader("Location");
        long ms = System.currentTimeMillis() - t0;
        log.info("[RES] {} {} -> {}{} ({} ms)",
                req.getMethod(), path, res.getStatus(),
                (loc != null ? " Location=" + loc : ""), ms);
    }
}
