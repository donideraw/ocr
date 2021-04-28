package com.doni.genbe.auth;

import com.doni.genbe.model.dto.AppAuth;
import com.doni.genbe.util.AppUtil;
import com.doni.genbe.util.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class TrailingLogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
        AppAuth appAuth = SecurityUtil.getAuth();
        if (appAuth != null) {
            log.info("REQ: {} - {} \n> {}", appAuth.getId(), appAuth.getUserType(), AppUtil.getRequestUrlAndMethod(httpServletRequest));
        } else {
            log.info("REQ: {} - {} \n> {}", "not authenticated", "not authenticated", AppUtil.getRequestUrlAndMethod(httpServletRequest));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
