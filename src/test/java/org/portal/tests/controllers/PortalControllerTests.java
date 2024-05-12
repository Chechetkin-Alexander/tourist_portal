package org.portal.tests.controllers;

import org.portal.controllers.PortalController;
import org.portal.services.CardService;
import org.portal.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PortalControllerTests {
    @Mock
    private UserService userService;
    @Mock
    private CardService cardService;
    @Mock
    private Model model;
    @InjectMocks
    private PortalController controller;

    @Test
    public void PortalTest() {
        when(cardService.findFree()).thenReturn(new ArrayList<>());
        when(userService.find(anyString())).thenReturn(null);

        assertEquals(controller.portal(model, "test"), "portal");
    }
}