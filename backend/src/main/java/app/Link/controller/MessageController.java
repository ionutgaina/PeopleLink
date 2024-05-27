package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.message.MessageDto;
import app.Link.dto.message.MessageSendDto;
import app.Link.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendDto message) {
        try {
            messageService.sendMessage(message);
            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/getContact")
    public ResponseEntity<?> getContact(@RequestBody ContactAddDto contactAddDto) {
        try {
            List<MessageDto> messageList = messageService.getContactMessages(contactAddDto);
            return ResponseEntity.ok().body(messageList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
