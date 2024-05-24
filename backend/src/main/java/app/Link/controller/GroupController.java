package app.Link.controller;

import app.Link.dto.group.GroupDescriptionDto;
import app.Link.dto.group.GroupDto;
import app.Link.dto.group.GroupRemoveDto;
import app.Link.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<?> createGroup(@RequestBody GroupDto groupDto) {
        try {
            groupService.createGroup(groupDto);
            return ResponseEntity.ok().body("Group created!");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/remove")
    public ResponseEntity<?> removeGroup(@RequestBody GroupRemoveDto groupRemoveDto) {
        try {
            groupService.removeGroup(groupRemoveDto);
            return ResponseEntity.ok().body("Group removed!");
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
}
