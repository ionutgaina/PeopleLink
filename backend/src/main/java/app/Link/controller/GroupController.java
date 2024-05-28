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
    public ResponseEntity<?> createGroup(@RequestBody GroupCreateDto groupCreateDto) {
        try {
            groupService.createGroup(groupCreateDto);
            messagingTemplate.convertAndSendToUser(
                    groupCreateDto.getOwnerName(),
                    "queue/rooms",
                    "Group " + groupCreateDto.getGroupName() + " has been created"
            );
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
            List<String> toNotify = groupService.getGroup(groupRemoveDto.getGroupName()).getMembers();
            for (String username : toNotify) {
                if (username.equals(groupRemoveDto.getRemoveUserName())) {
                    messagingTemplate.convertAndSendToUser(
                            username,
                            "queue/rooms",
                            "You have been removed from the group " + groupRemoveDto.getGroupName()
                    );
                } else {
                    messagingTemplate.convertAndSendToUser(
                            username,
                            "queue/rooms",
                            "User " + groupRemoveDto.getRemoveUserName() + " has been removed from group " + groupRemoveDto.getGroupName()
                    );
                }
            }

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
                    groupInviteDto.getAdminName() + " invited you to group " + groupInviteDto.getGroupName()
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
            List<String> toNotify = groupService.getGroup(groupLeaveDto.getGroupName()).getMembers();
            for (String member : toNotify) {
                messagingTemplate.convertAndSendToUser(
                        member,
                        "queue/rooms",
                        "User " + groupLeaveDto.getUserName() + " has left the group"
                );
            }
            return ResponseEntity.ok().body("Left group!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> leaveGroup(@RequestBody GroupJoinDto groupJoinDto) {
        try {
            groupService.joinGroup(groupJoinDto);
            List<String> toNotify = groupService.getGroup(groupJoinDto.getGroupName()).getMembers();
            for (String username : toNotify) {
                messagingTemplate.convertAndSendToUser(
                        username,
                        "queue/rooms",
                        "User " + groupJoinDto.getUserName() + " has joined the group"
                );
            }
            return ResponseEntity.ok().body("Group joined!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("{groupName}")
    public ResponseEntity<?> getGroup(@PathVariable String groupName) {
        try {
            GroupDto group = groupService.getGroup(groupName);
            return ResponseEntity.ok().body(group);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getUserGroups(@PathVariable String username) {
        try {
            List<GroupDto> groups = groupService.getGroups(username);
            return ResponseEntity.ok().body(groups);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}
