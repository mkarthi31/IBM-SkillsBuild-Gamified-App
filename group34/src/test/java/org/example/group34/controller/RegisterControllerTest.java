package org.example.group34.controller;

import org.example.group34.model.User;
import org.example.group34.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RegisterControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        User user = new User();
        String viewName = registerController.register(user, model);
        assertEquals("register", viewName);
    }

    @Test
    void testRegisterUser_WithValidationErrors() {
        User user = new User();
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = registerController.registerUser(user, bindingResult, model);
        assertEquals("register", viewName);
        verify(userService, never()).registerUser(any(), any());
    }

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = registerController.registerUser(user, bindingResult, model);
        assertEquals("redirect:/login", viewName);
        verify(userService, times(1)).registerUser("testUser", "testPass");
    }
}
