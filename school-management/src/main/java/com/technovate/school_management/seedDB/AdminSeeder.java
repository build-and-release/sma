package com.technovate.school_management.seedDB;

import com.technovate.school_management.entity.User;
import com.technovate.school_management.entity.enums.UserRoles;
import com.technovate.school_management.repository.UserRepository;
import com.technovate.school_management.service.contracts.UserService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements CommandLineRunner {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AdminSeeder.class);
    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        try {
            Dotenv dotenv = Dotenv.load();
            String username = dotenv.get("DEFAULT_ADMIN_USERNAME");
            if (username == null) {
                logger.warn("No default admin username found");
                return;
            }
            ;
            String password = dotenv.get("DEFAULT_ADMIN_PASSWORD");
            if (password == null || password.isEmpty()) {
                logger.warn("No default password for admin user");
                return;
            }
            List<UserRoles> userRoles = new ArrayList<>(Arrays.asList(UserRoles.USER, UserRoles.ADMIN, UserRoles.STAFF));
            User user = userService.createUserWithRoles(username, password, userRoles);
            userRepository.save(user);
        } catch (Exception ex) {
            logger.warn("Unable to add super admin due to the following exception: {}", ex.getMessage());
        }
    }
}
