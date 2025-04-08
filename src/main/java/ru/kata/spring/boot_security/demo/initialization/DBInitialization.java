package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.Collections;

@Component
public class DBInitialization {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DBInitialization(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    //@PostConstruct
    public void init() {
        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.addRole(adminRole);

        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.addRole(userRole);

        User admin = new User();
        admin.setUserName("admin");
        admin.setPassword("admin123");
        admin.setEmail("admin@example.com");
        admin.setRoles(Collections.singleton(adminRole));
        userService.save(admin);

        User user = new User();
        user.setUserName("user");
        user.setPassword("user123");
        user.setEmail("user@example.com");
        user.setRoles(Collections.singleton(userRole));
        userService.save(user);
    }
}
