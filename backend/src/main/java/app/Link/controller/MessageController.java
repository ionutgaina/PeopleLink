package app.Link.controller;

import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.message.MessageDto;
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

            StringBuilder destTopic = new StringBuilder("/contacts/");

            if (message.getContactSenderName().compareTo(message.getContactReceiverName()) < 0) {
                destTopic.append(message.getContactSenderName()).append("_").append(message.getContactReceiverName());
            } else {
                destTopic.append(message.getContactReceiverName()).append("_").append(message.getContactSenderName());
            }

            System.out.println(destTopic);

            messagingTemplate.convertAndSend(
                    destTopic.toString(),
                    message.getSenderName() + " sent you a direct message"
            );

            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/getContact")
    public ResponseEntity<?> getContactMessages(@RequestBody ContactAddDto contactAddDto) {
        try {
            List<MessageDto> messageList = messageService.getContactMessages(contactAddDto);
            return ResponseEntity.ok().body(messageList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
