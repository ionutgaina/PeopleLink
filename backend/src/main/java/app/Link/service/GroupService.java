package app.Link.service;

import app.Link.common.MemberRole;
import app.Link.dto.group.GroupDto;
import app.Link.model.Group;
import app.Link.model.GroupMember;
import app.Link.model.User;
import app.Link.repository.GroupMemberRepository;
import app.Link.repository.GroupRepository;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public void createGroup(GroupDto groupDto) {
        if (groupRepository.findByName(groupDto.getName()).isPresent()) {
            throw new RuntimeException("Group already exists");
        }
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());

        User owner = userRepository.findByUsername(groupDto.getOwner()).orElseThrow(
            () -> new RuntimeException("Owner not found")
        );

        GroupMember ownerMember = new GroupMember();
        ownerMember.setGroup(group);
        ownerMember.setUser(owner);
        ownerMember.setRole(MemberRole.ADMIN);

        groupRepository.save(group);
        groupMemberRepository.save(ownerMember);

        for (String member : groupDto.getMembers()) {
            User user = userRepository.findByUsername(member).orElse(null);
            if (user == null) {
                continue;
            }
            GroupMember groupMember = new GroupMember();
            groupMember.setGroup(group);
            groupMember.setUser(user);

            groupMemberRepository.save(groupMember);
        }
    }
}
