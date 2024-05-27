package app.Link.controller;

import app.Link.dto.groupMember.GroupMemberDto;
import app.Link.dto.groupMessage.MessageSendDto;
import app.Link.dto.groupMessage.MessageDto;
import app.Link.repository.GroupRepository;
import app.Link.service.GroupMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/gmessages")
public class GroupMessageController {
    private final GroupMessageService groupMessageService;
    private final GroupRepository groupRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendGroupMessage(@RequestBody MessageSendDto messageDto) {
        try {
            groupMessageService.sendMessage(messageDto);
            return ResponseEntity.ok().body("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/getGroup")
    public ResponseEntity<?> getContact(@RequestBody GroupMemberDto groupMemberDto) {
        try {
            List<MessageDto> messageList = groupMessageService.getGroupMessages(groupMemberDto);
            return ResponseEntity.ok().body(messageList);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
