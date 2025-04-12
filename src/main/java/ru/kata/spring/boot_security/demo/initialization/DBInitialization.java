package ru.kata.spring.boot_security.demo.initialization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DBInitialization {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DBInitialization(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //@PostConstruct
    public void initUsers() {
        Role adminRole = new Role(1L, "ADMIN");
        Role userRole = new Role(2L, "USER");
        roleService.addRole(adminRole);
        roleService.addRole(userRole);

        User admin = new User("admin", "admin123",
                (byte) 23, "admin@ex.com", List.of(adminRole, userRole));
        User user1 = new User("user", "user123",
                (byte) 18, "user@ex.com", List.of(userRole));
        userService.save(admin);
        userService.save(user1);
    }
}
