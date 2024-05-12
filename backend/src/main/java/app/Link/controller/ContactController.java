package app.Link.controller;

import app.Link.model.User;
import app.Link.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ContactController {
    private final ContactService userService;


    public void addContact(User user, User contact) {
        userService.addContact(user, contact);
    }

    public void removeContact(User user, User contact) {
        userService.removeContact(user, contact);
    }
}
