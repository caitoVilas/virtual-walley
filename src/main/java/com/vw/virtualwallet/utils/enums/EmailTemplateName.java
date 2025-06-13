package com.vw.virtualwallet.utils.enums;

import lombok.Getter;

/**
 * @author caito Vilas
 * date: 08/2024
 * This enum is used to define the names of email templates.
 */
@Getter
public enum EmailTemplateName {
    ACTIVATE_ACCOUNT("activate_account");

    private final String name;

    EmailTemplateName(String name) {
        this.name = name;
    }
}
