package com.technovate.school_management.service.contracts;

public interface UsersCredentialsGenerator {
    String generateUsername(String firstName, String lastName);
    String generateIdNumber();
}
