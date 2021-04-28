package com.doni.genbe.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.doni.genbe.helper.AppUserType;
import com.doni.genbe.helper.Constant;
import com.doni.genbe.model.dto.AppAuth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Component
public class AuthoFilter extends OncePerRequestFilter {
    @Value("${app.jwt.secret-key:s14Pb0Ss_d0S3cR#t}")
    private String jwtSecretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        log.info("REQ: {}", AppUtil.getRequestUrlAndMethod(request));
        String authorization = request.getHeader(Constant.HEADER_AUTHORIZATION);

        if (StringUtils.isEmpty(authorization)) {
            chain.doFilter(request, response);
            return;
        }
        // type Bearer
        if (authorization.startsWith(Constant.AUTHORIZATION_TYPE_BEARER)) {
            AppAuth authentication = getAuthentication(authorization);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
            return;
        }
        // type Key
        if (authorization.startsWith(Constant.AUTHORIZATION_TYPE_KEY)) {
            chain.doFilter(request, response);
            return;
        }
        chain.doFilter(request, response);
    }

    private AppAuth getAuthentication(String authorization) {
        String phoneNo = null;
        long id = 0L;
        AppUserType userType = null;
        try {
            // parse the token.
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(jwtSecretKey.getBytes()))
                    .build()
                    .verify(authorization.substring(Constant.AUTHORIZATION_TYPE_BEARER.length() + 1));
            phoneNo = decodedJWT.getSubject();
            id = !decodedJWT.getClaim("id").isNull() ? decodedJWT.getClaim("id").asLong() : 0;
            userType = decodedJWT.getClaim(AppUserType.class.getSimpleName()).isNull() ?
                    null :
                    AppUserType.valueOf(decodedJWT.getClaim(AppUserType.class.getSimpleName()).asString());
        } catch (Exception e) {
            log.info("validate token failed, {}", e.getMessage());
        }
        if (phoneNo != null) {
            AppAuth appAuth = new AppAuth(phoneNo, null, Collections.emptyList());
            appAuth.setId(id);
            appAuth.setUserType(userType);
            return appAuth;
        }
        return null;
    }
}

