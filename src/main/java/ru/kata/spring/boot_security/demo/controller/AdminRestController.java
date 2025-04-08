package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.AdminResponse;
import ru.kata.spring.boot_security.demo.util.EditUserResponse;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    // Показать список пользователей
    @GetMapping
    public ResponseEntity<?> showUserList(
            @RequestParam(value = "editId", required = false) Long editId,
            @RequestParam(value = "deleteId", required = false) Long deleteId,
            @AuthenticationPrincipal User authUser) {

        return ResponseEntity.ok()
                .body(new AdminResponse(
                        authUser,
                        userService.findAll(),
                        roleService.getRoles(),
                        editId != null ? userService.findById(editId) : null,
                        deleteId != null ? userService.findById(deleteId) : null
                ));
    }

    // Создать нового пользователя
    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed");
        }
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Редактировать пользователя
    @GetMapping("/edit/{id}")
    public ResponseEntity<?> editUser(@PathVariable("id") Long id, @AuthenticationPrincipal User authUser) {
        User userToEdit = userService.findById(id);
        return ResponseEntity.ok()
                .body(new EditUserResponse(authUser, userService.findAll(), roleService.getRoles(), userToEdit));
    }

    // Обновить пользователя
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        userService.update(user);
        return ResponseEntity.ok(user);
    }

    // Удалить пользователя
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
