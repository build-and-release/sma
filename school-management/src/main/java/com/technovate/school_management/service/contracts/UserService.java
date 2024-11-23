package com.technovate.school_management.service.contracts;

import com.technovate.school_management.entity.User;
import com.technovate.school_management.entity.enums.UserRoles;

import java.util.List;

public interface UserService {
    User createUserWithRoles(String username, String password, List<UserRoles> roles);
}
