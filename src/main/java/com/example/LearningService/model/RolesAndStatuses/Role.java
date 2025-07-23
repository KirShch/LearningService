package com.example.LearningService.model.RolesAndStatuses;

public enum Role {
    STUDENT("STUDENT"),
    TEACHER("TEACHER"),
    ADMIN("ADMIN");

    private final String value;

    Role(String value) {this.value = value;}

    public String getValue() {
        return value;
    }
}
