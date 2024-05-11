package org.portal;

import org.portal.models.Role;
import org.portal.services.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        RoleService roleService = context.getBean(RoleService.class);

        if (roleService.findByName("organizer") == null || roleService.findByName("participant") == null) {
            createRoles(roleService);
        }
    }

    private static void createRoles(RoleService roleService) {
        Role organizerRole = new Role();
        organizerRole.setName("organizer");
        organizerRole.setId(1);

        Role participantRole = new Role();
        participantRole.setName("participant");
        participantRole.setId(2);

        roleService.create(organizerRole);
        roleService.create(participantRole);
    }
}