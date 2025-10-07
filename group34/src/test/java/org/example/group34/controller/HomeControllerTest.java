package org.example.group34.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    private HomeController homeController;
    private Model model;
    private SecurityContext securityContext;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        homeController = new HomeController();
        model = mock(Model.class);
        securityContext = mock(SecurityContext.class);
        authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void welcome_shouldReturnWelcomePageWithAuthenticatedUser() {
        UserDetails userDetails = mock(UserDetails.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testUser");

        String viewName = homeController.welcome(model);

        verify(model).addAttribute("username", "testUser");
        assertEquals("welcome", viewName);
    }

    @Test
    void welcome_shouldReturnWelcomePageWithGuestUser() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        String viewName = homeController.welcome(model);

        verify(model).addAttribute("username", "Guest");
        assertEquals("welcome", viewName);
    }
}
