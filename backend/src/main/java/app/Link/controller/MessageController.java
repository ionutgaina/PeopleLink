package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.message.MessageDto;
import app.Link.dto.message.MessageGetDto;
import app.Link.dto.message.MessageSendDto;
import app.Link.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendDto message) {
        try {
            messageService.sendMessage(message);

            String destTopic = "/contacts/" + message.getRoomCode();

            messagingTemplate.convertAndSend(
                    destTopic,
                    message.getSenderName() + " sent you a direct message"
            );

            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/getContact")
    public ResponseEntity<?> getContactMessages(@RequestBody MessageGetDto messageGetDto) {
        try {
            List<MessageDto> messageList = messageService.getContactMessages(messageGetDto);
            return ResponseEntity.ok().body(messageList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


}
