package app.Link.controller;

import app.Link.dto.user.UserGetDto;
import app.Link.dto.user.UserRegisterDto;
import app.Link.model.User;
import app.Link.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            userService.registerUser(userRegisterDto);
            return ResponseEntity.ok().body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            User authenticatedUser = userService.authenticateUser(userRegisterDto.getUsername(), userRegisterDto.getPassword());
            return ResponseEntity.ok().body("Authenticated user: " + authenticatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findUsers() {
        return ResponseEntity.ok(userService.findUsers());
    }
}
