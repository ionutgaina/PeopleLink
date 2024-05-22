package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add_contact")
    public ResponseEntity<?> addContact(@RequestBody ContactAddDto contact) {
        return ResponseEntity.notFound().build();
    }
}
