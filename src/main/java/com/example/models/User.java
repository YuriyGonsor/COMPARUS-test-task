package com.example.models;

import java.util.Objects;

public record User(String id, String username, String name, String surname) {

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        return Objects.equals(id, user.id)
                && Objects.equals(username, user.username)
                && Objects.equals(name, user.name)
                && Objects.equals(surname, user.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, name, surname);
    }
}
