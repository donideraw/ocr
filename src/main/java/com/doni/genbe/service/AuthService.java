package com.doni.genbe.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.doni.genbe.helper.AppUserType;
import com.doni.genbe.model.dto.LoginDtoReq;
import com.doni.genbe.model.dto.LoginDtoRes;
import com.doni.genbe.model.dto.RegisterDto;
import com.doni.genbe.model.dto.AppException;
import com.doni.genbe.model.entity.AppUser;
import com.doni.genbe.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class AuthService {
    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.jwt.secret-key:s14Pb0Ss_d0S3cR#t}")
    private String jwtSecretKey;

    public AuthService(AppUserRepository appUserRepository, BCryptPasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginDtoRes login(LoginDtoReq req) {
        AppUser appUser = appUserRepository.findByUsername(req.getUsername());
        if (appUser == null) {
            throw new AppException("user with no " + req.getUsername() + " not found.");
        }
        if (passwordEncoder.matches(req.getPassword(), appUser.getPassword())) {
            String token = JWT.create()
                    .withSubject(appUser.getUsername())
                    .withClaim("id", appUser.getId())
                    .withClaim(AppUserType.class.getSimpleName(), appUser.getUserType().name())
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC512(jwtSecretKey.getBytes()));
            LoginDtoRes loginDtoRes = new LoginDtoRes();
            loginDtoRes.setUsername(appUser.getUsername());
            loginDtoRes.setToken(token);
            loginDtoRes.setUserType(appUser.getUserType());
            return loginDtoRes;
        }
        throw new AppException("password incorrect!");
    }

    public AppUser addUserOperator(RegisterDto dto) {
        AppUser newUser = new AppUser();
        newUser.setUsername(dto.getUsername());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        newUser.setUserType(AppUserType.OPERATOR);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCreatedBy(1L);
        return appUserRepository.save(newUser);
    }

}
