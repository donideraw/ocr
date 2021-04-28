package com.doni.genbe.util;

import com.doni.genbe.helper.AppUserType;
import com.doni.genbe.model.dto.AppAuth;
import com.doni.genbe.model.dto.AppException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static AppAuth getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AppAuth) {
            return (AppAuth) authentication;
        } else if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return AppAuth.fromUsernamePasswordAuthenticationToken((UsernamePasswordAuthenticationToken) authentication);
        }
        return null;
    }

    public static void userTypeMustBe(AppUserType originUserType, AppUserType... shouldBeOneOfThisUserType) {
        for (AppUserType userType : shouldBeOneOfThisUserType) {
            if (userType == originUserType)
                return;
        }
        throw new AppException("unauthorized access");
    }
}
