package com.gom.money_help.model;

import lombok.Getter;

@Getter
public enum Categories {
    HOUSING("housing"),
    FOOD("food"),
    TRANSPORT("transport"),
    HEALTH("health"),
    ENTERTAINMENT("entertainment"),
    EDUCATION("education"),
    CLOTHING("clothing"),
    OTHERS("others");

    private final String category;
    Categories(String category) { this.category = category; }

}
