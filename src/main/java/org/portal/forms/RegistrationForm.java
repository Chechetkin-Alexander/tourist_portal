package org.portal.forms;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class RegistrationForm {
    @NotNull
    @Size(min = 3, max = 40)
    @Getter @Setter public String login;

    @NotNull
    @Size(min = 6)
    @Getter @Setter private String password;

    @NotNull
    @Size(min = 2)
    @Getter @Setter private String name;

    @NotNull
    @Size(min = 2) @Getter @Setter private String surname;

    @Min(18)
    @Max(200)
    @Getter @Setter private int age;

    @Size(max = 50)
    @Getter @Setter private String address;

    @Size(max=255)
    @Getter @Setter private String organization;

    @Getter @Setter private String information;

    @Getter @Setter private String resume;

    @NotNull
    @Getter @Setter private long roleId;
}