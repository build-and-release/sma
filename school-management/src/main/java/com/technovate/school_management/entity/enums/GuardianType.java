package com.technovate.school_management.entity.enums;

public enum GuardianType {
    PARENT("Parent"),
    LEGAL_GUARDIAN("Legal Guardian");

    private final String guardianType;
    GuardianType(String guardianType) {
        this.guardianType = guardianType;
    }
    String getGuardianType() {
        return this.guardianType;
    }
}
