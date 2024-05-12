package app.Link.controller;

import app.Link.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void blockUser(Long senderId, Long receiverId) {
        userService.blockUser(senderId, receiverId);
    }
}
