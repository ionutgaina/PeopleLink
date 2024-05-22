package app.Link.controller;

import app.Link.repository.ContactRepository;
import app.Link.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;
}
