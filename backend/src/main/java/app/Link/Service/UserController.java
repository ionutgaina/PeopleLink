package app.Link.Service;

import app.Link.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public void addContact(User user, User contact) {
        userService.addContact(user, contact);
    }

    public void removeContact(User user, User contact) {
        userService.removeContact(user, contact);
    }
}
