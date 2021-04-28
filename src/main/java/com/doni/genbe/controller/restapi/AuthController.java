package com.doni.genbe.controller.restapi;

import com.doni.genbe.helper.Constant;
import com.doni.genbe.model.dto.LoginDtoReq;
import com.doni.genbe.model.dto.LoginDtoRes;
import com.doni.genbe.model.dto.RegisterDto;
import com.doni.genbe.model.entity.FolderPath;
import com.doni.genbe.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register/operator")
    public ResponseEntity<?> savePath(@RequestBody RegisterDto dto) {
        return ResponseEntity.ok(service.addUserOperator(dto));
    }

    @PostMapping(value = "auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> validateOtpCode(HttpServletRequest req, HttpServletResponse resp, @RequestBody LoginDtoReq loginReq) {
        LoginDtoRes result = service.login(loginReq);
//        resp.addHeader(Constant.HEADER_AUTHORIZATION, "Bearer " + result.getToken());
        return ResponseEntity.ok(result);
    }
}
