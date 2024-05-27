package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.contact.ContactSendDto;
import app.Link.dto.user.UserGetDto;
import app.Link.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/")
    public ResponseEntity<?> addContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.addContact(contact);
            messagingTemplate.convertAndSendToUser(
                    contact.getReceiver(),
                    "/queue/contacts",
                    "You have a new friend request from " + contact.getSender()
            );
            return ResponseEntity.ok().body("Friend request sent!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    //   probabil nu e nevoie de asta
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
            messagingTemplate.convertAndSendToUser(
                    contact.getSender(),
                    "/queue/contacts",
                    "Friend request accepted by: " + contact.getReceiver()
            );
            return ResponseEntity.ok().body("Friend request accepted!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.rejectContact(contact);
            messagingTemplate.convertAndSendToUser(
                    contact.getSender(),
                    "/queue/contacts",
                    "Friend request rejected by: " + contact.getReceiver()
            );
            return ResponseEntity.ok().body("Friend request rejected!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

//    probabil nu e nevoie de asta
    @GetMapping("/sent")
    public ResponseEntity<?> getSentContacts(@RequestBody UserGetDto user) {
        try {
            List<ContactAddDto> sentRequests = contactService.getSentRequests(user);
            return ResponseEntity.ok(sentRequests);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    // probabil nu e nevoie de asta
    @PostMapping("/cancel")
    public ResponseEntity<?> cancelContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.cancelRequest(contact);
            return ResponseEntity.ok().body("Friend request cancelled!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{username}")
    @ResponseBody
    public ResponseEntity<?> getContacts(@PathVariable String username) {
        try {
            List<ContactSendDto> contacts = contactService.getContacts(username);
            return ResponseEntity.ok(contacts);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }


//    probabil nu e nevoie de asta
    @PostMapping("/block")
    public ResponseEntity<?> blockContact(@RequestBody ContactAddDto contact) {
        try {
            contactService.blockContact(contact);
            return ResponseEntity.ok().body("User blocked!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

//    probabil nu e nevoie de asta
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
