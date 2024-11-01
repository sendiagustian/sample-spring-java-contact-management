package id.sendistudio.spring.base.app.middlewares;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.filter.OncePerRequestFilter;

import id.sendistudio.spring.base.constants.ExcludeEndpoint;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilterRequestMiddleware extends OncePerRequestFilter {

    private static final String API_KEY_HEADER = "LOG_SERVICE_TRX";

    @Autowired
    ExcludeEndpoint excludeEndpoint;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        String logServiceTrx = request.getHeader(API_KEY_HEADER);

        List<String> excludes = excludeEndpoint.getExcludes();
        String requestURI = request.getRequestURI();

        if (excludes.stream().anyMatch(requestURI::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        if (logServiceTrx == null || logServiceTrx.isEmpty()) {
            throw new MissingServletRequestParameterException(API_KEY_HEADER, "Header");
        }

        // LOG_SERVICE_TRX ada, teruskan request
        log.info("LOG_SERVICE_TRX: {}", logServiceTrx);
        filterChain.doFilter(request, response);
    }
}
