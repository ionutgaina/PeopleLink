package app.Link.controller;

import app.Link.dto.groupMember.GroupMemberDto;
import app.Link.dto.groupMessage.MessageSendDto;
import app.Link.dto.groupMessage.MessageDto;
import app.Link.service.GroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gmessages")
public class GroupMessageController {
    private final GroupMessageService groupMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    public ResponseEntity<?> sendGroupMessage(@RequestBody MessageSendDto messageDto) {
        try {
            groupMessageService.sendMessage(messageDto);
            messagingTemplate.convertAndSend(
                    "/groups/" + messageDto.getGroupName(),
                    messageDto.getSenderName() + " sent message to group " + messageDto.getGroupName()
            );
            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/getGroup")
    public ResponseEntity<?> getContact(@RequestBody GroupMemberDto groupMemberDto) {
        try {
            List<MessageDto> messageList = groupMessageService.getGroupMessages(groupMemberDto);
            return ResponseEntity.ok().body(messageList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
