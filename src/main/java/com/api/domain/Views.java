package com.api.domain;

public interface Views {
    interface Admin extends Manager {}
    interface Manager extends BasicUser {}
    interface BasicUser {}

    static Class<?> resolveFromRole(String role) {
        if (role.startsWith("ROLE_")) {
            return resolveFromRole(Role.valueOf(role.substring(5)));
        }
        return resolveFromRole(Role.valueOf(role));
    }

    static Class<?> resolveFromRole(Role role) {
        if (role == Role.ADMIN) {
            return Views.Admin.class;
        } else if (role == Role.MANAGER) {
            return Views.Manager.class;
        } else {
            return Views.BasicUser.class;
        }
    }
}
