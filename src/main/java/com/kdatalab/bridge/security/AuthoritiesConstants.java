package com.kdatalab.bridge.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    // Администратор системный рол. Все - Полные права (Супер пользователь)
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String INSPECTOR = "ROLE_INSPECTOR";

    // Пользователь - обычный*/
    public static final String USER = "ROLE_USER";
    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    private AuthoritiesConstants() {
    }
}
