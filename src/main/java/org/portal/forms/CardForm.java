package org.portal.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

public class CardForm {
    @NotNull
    @Size(min = 2)
    @Getter @Setter private String name;

    @NotNull
    @Getter @Setter private int price;

    @Getter @Setter private String description;
}