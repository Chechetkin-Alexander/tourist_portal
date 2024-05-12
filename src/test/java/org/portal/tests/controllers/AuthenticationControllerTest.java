package org.portal.tests.controllers;

import org.portal.controllers.AuthenticationController;
import org.portal.forms.RegistrationForm;
import org.portal.models.User;
import org.portal.services.RoleService;
import org.portal.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthenticationControllerTest {
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @InjectMocks
    private AuthenticationController controller;

    @Test
    public void loginTest() {
        assertEquals(controller.login(), "login-form");
    }

    @Test
    public void organizerRegistrationFormTest() {
        assertEquals(controller.organizerRegistration(model), "organizer-registration-form");
    }

    @Test
    public void participantRegistrationFormTest() {
        assertEquals(controller.participantRegistration(model), "participant-registration-form");
    }

    @Test
    public void existentUserRegistrationTest() {
        RegistrationForm mockForm = new RegistrationForm();
        mockForm.setLogin("test");
        mockForm.setName("test");

        BindingResult mockResult = mock(BindingResult.class);

        when(userService.find(anyString())).thenReturn(new User());
        controller.registrationSave(mockForm, mockResult);

        verify(mockResult).rejectValue("login", "1", "Login exists");
    }

    @Test
    public void anonymousUserTest() {
        RegistrationForm mockForm = new RegistrationForm();
        mockForm.setLogin("test");
        mockForm.setName("anonymousUser");

        BindingResult mockResult = mock(BindingResult.class);

        when(userService.find(anyString())).thenReturn(null);
        controller.registrationSave(mockForm, mockResult);

        verify(mockResult).rejectValue("login", "2", "Forbidden login");
    }

    @Test
    public void errorsTest() {
        RegistrationForm mockForm = new RegistrationForm();
        mockForm.setLogin("test");
        mockForm.setName("test");

        BindingResult mockResult = mock(BindingResult.class);

        when(mockResult.hasErrors()).thenReturn(true);
        when(userService.find(anyString())).thenReturn(null);
        assertEquals(controller.registrationSave(mockForm, mockResult), "redirect:/authentication/organizer/registration");
    }
}