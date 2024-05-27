package app.Link.controller;

import app.Link.dto.group.*;
import app.Link.dto.group.GroupDescriptionDto;
import app.Link.dto.group.GroupDto;
import app.Link.dto.group.GroupInviteDto;
import app.Link.dto.group.GroupRemoveDto;
import app.Link.dto.group.GroupRemoveUserDto;
import app.Link.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto groupDto) {
        try {
            groupService.createGroup(groupDto);
            for (String username : groupDto.getMembers()) {
                messagingTemplate.convertAndSendToUser(
                        username,
                        "queue/rooms",
                        "User " + groupDto.getOwnerName() + " invited you to group " + groupDto.getGroupName()
                );
            }
            return ResponseEntity.ok().body("Group created!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeGroup(@RequestBody GroupRemoveDto groupRemoveDto) {
        try {
            List<String> toNotify = groupService.removeGroup(groupRemoveDto);
            for (String username : toNotify) {
                messagingTemplate.convertAndSendToUser(
                        username,
                        "queue/rooms",
                        "Group " + groupRemoveDto.getGroupName() + " has been removed"
                );
            }
            return ResponseEntity.ok().body("Group removed!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/removeMember")
    public ResponseEntity<?> removeMember(@RequestBody GroupRemoveUserDto groupRemoveDto) {
        try {
            groupService.removeMember(groupRemoveDto);
            messagingTemplate.convertAndSendToUser(
                    groupRemoveDto.getRemoveUserName(),
                    "queue/rooms",
                    "You have been removed from the group " + groupRemoveDto.getGroupName()
            );
            return ResponseEntity.ok().body("Member removed!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/invite")
    public ResponseEntity<?> inviteToGroup(@RequestBody GroupInviteDto groupInviteDto) {
        try {
            groupService.inviteUser(groupInviteDto);
            messagingTemplate.convertAndSendToUser(
                    groupInviteDto.getUserName(),
                    "queue/rooms",
                    groupInviteDto.getAdminName() + " invited to group " + groupInviteDto.getGroupName()
            );
            return ResponseEntity.ok().body("User added!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/changeDesc")
    public ResponseEntity<?> changeDescription(@RequestBody GroupDescriptionDto groupDto) {
        try {
            groupService.changeDescription(groupDto);
            return ResponseEntity.ok().body("Description changed!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leaveGroup(@RequestBody GroupLeaveDto groupLeaveDto) {
        try {
            groupService.leaveGroup(groupLeaveDto);
            return ResponseEntity.ok().body("Left group!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}
