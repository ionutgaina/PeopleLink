package app.Link.controller;

import app.Link.dto.groupMember.GroupMemberDto;
import app.Link.dto.groupMessage.GroupMessageDto;
import app.Link.dto.groupMessage.GroupMessageSendDto;
import app.Link.dto.message.MessageDto;
import app.Link.dto.message.MessageGetDto;
import app.Link.dto.message.MessageSendDto;
import app.Link.service.GroupMessageService;
import app.Link.service.MessageService;
import app.Link.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final GroupMessageService groupMessageService;
    private final S3Service s3Service;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageSendDto message) {
        String destTopic = "/rooms/" + message.getRoomCode();
        try {

            if (!message.getRoomCode().contains(message.getSenderName())) {
                messageService.sendMessage(message);
                messagingTemplate.convertAndSend(
                        destTopic,
                        message.getSenderName() + "sent you a message"
                );
            } else {
                GroupMessageSendDto groupMessageDto = new GroupMessageSendDto(
                        message.getText(),
                        message.getSenderName(),
                        message.getRoomCode()
                );
                groupMessageService.sendMessage(groupMessageDto);
                messagingTemplate.convertAndSend(
                        destTopic,
                        message.getSenderName() + " sent a message in " + message.getRoomCode()
                );
            }

            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping(value="/sendFile")
    public ResponseEntity<?> sendMessage(
                                         @RequestParam("file") MultipartFile file) {
        try {
            // Creează DTO-ul MessageSendDto
            String fileUrl = null;
            System.out.println(file);
            // Procesează atașamentul dacă există
            if (file != null && !file.isEmpty()) {
                System.out.println("File received");
                fileUrl = s3Service.uploadFile(file); // Implementați metoda uploadFile în MessageService
                System.out.println(fileUrl);
            }
            MessageSendDto message = new MessageSendDto("text", "senderName", "roomCode", fileUrl);

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

    @PostMapping("/")
    public ResponseEntity<?> getContactMessages(@RequestBody MessageGetDto messageGetDto) {
        try {
            if (!messageGetDto.getRoomCode().contains(messageGetDto.getUserName())) {
                List<MessageDto> messageList = messageService.getContactMessages(messageGetDto);
                return ResponseEntity.ok().body(messageList);
            } else {
                GroupMemberDto groupMemberDto = new GroupMemberDto(
                        messageGetDto.getRoomCode(),
                        messageGetDto.getUserName()
                );
                List<GroupMessageDto> groupMessageList = groupMessageService.getGroupMessages(groupMemberDto);
                return ResponseEntity.ok().body(groupMessageList);
            }
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
