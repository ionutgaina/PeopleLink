package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.user.UserGetDto;
import app.Link.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;

    @PostMapping("/add")
    public ResponseEntity<?> addContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.addContact(contact);
            return ResponseEntity.ok().body("Friend request sent!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingContacts(@RequestBody UserGetDto user) {
        try {
            List<ContactAddDto> pendingContacts = contactService.getPendingContacts(user);
            return ResponseEntity.ok(pendingContacts);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.acceptContact(contact);
            return ResponseEntity.ok().body("Friend request accepted!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.rejectContact(contact);
            return ResponseEntity.ok().body("Friend request rejected!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/sent")
    public ResponseEntity<?> getSentContacts(@RequestBody UserGetDto user) {
        try {
            List<ContactAddDto> sentRequests = contactService.getSentRequests(user);
            return ResponseEntity.ok(sentRequests);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.cancelRequest(contact);
            return ResponseEntity.ok().body("Friend request cancelled!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getContacts(@RequestBody UserGetDto user) {
        try {
            List<ContactAddDto> contacts = contactService.getContacts(user);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/block")
    public ResponseEntity<?> blockContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.blockContact(contact);
            return ResponseEntity.ok().body("User blocked!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/unblock")
    public ResponseEntity<?> unblockContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.unblockContact(contact);
            return ResponseEntity.ok().body("User unblocked!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}
