package com.tejastomar.journalapp.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SecurityUtilTests {

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void getCurrentUsername_returnsAuthenticatedUsername() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("tejas", null, Collections.emptyList())
        );

        assertEquals("tejas", SecurityUtil.getCurrentUsername());
    }

    @Test
    void getCurrentUsername_throwsWhenNoUserIsAuthenticated() {
        assertThrows(IllegalStateException.class, SecurityUtil::getCurrentUsername);
    }
}
