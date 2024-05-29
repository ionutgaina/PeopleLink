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

    @PostMapping(value = "/send", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<?> sendMessage(
            @RequestPart(value = "text", required = false) String text,
            @RequestPart("senderName") String senderName,
            @RequestPart("roomCode") String roomCode,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        try {
            if (text == null || text.isEmpty()) {
                text = "";
            }
            // Process the file if it exists
            String fileUrl = null;
            if (file != null && !file.isEmpty()) {
                fileUrl = s3Service.uploadFile(file);
            }

            // Create the message DTO
            MessageSendDto message = new MessageSendDto(text, senderName, roomCode, fileUrl);

            // Determine the destination topic and send the message
            String destTopic = "/rooms/" + roomCode;
            if (roomCode.contains(senderName)) {
                messageService.sendMessage(message);
                messagingTemplate.convertAndSend(destTopic, senderName + " sent you a message");
            } else {
                GroupMessageSendDto groupMessageDto = new GroupMessageSendDto(senderName, text, roomCode);
                groupMessageService.sendMessage(groupMessageDto);
                messagingTemplate.convertAndSend(destTopic, senderName + " sent a message in " + roomCode);
            }

            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> getContactMessages(@RequestBody MessageGetDto messageGetDto) {
        try {
            if (messageGetDto.getRoomCode().contains(messageGetDto.getUserName())) {
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
