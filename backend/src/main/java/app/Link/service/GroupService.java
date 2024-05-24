package app.Link.service;

import app.Link.common.MemberRole;
import app.Link.dto.group.GroupDescriptionDto;
import app.Link.dto.group.GroupDto;
import app.Link.dto.group.GroupMemberDto;
import app.Link.dto.group.GroupRemoveDto;
import app.Link.model.Group;
import app.Link.model.GroupMember;
import app.Link.model.User;
import app.Link.repository.GroupMemberRepository;
import app.Link.repository.GroupRepository;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;
    private final DataSourceTransactionManagerAutoConfiguration dataSourceTransactionManagerAutoConfiguration;

    public void createGroup(GroupDto groupDto) throws Exception {
        if (groupRepository.findByName(groupDto.getName()).isPresent()) {
            throw new Exception("Group already exists");
        }
        Group group = new Group();
        group.setName(groupDto.getName());
        group.setDescription(groupDto.getDescription());
        groupRepository.save(group);

        User owner = userRepository.findByUsername(groupDto.getOwner()).orElseThrow(
            () -> new Exception("Owner not found")
        );

        addUser(new GroupMemberDto(groupDto.getName(), owner.getUsername(), MemberRole.ADMIN));

        for (String member : groupDto.getMembers()) {
            addUser(new GroupMemberDto(groupDto.getName(), member, MemberRole.MEMBER));
        }
    }

    public void addUser(GroupMemberDto groupMemberDto) throws Exception {
        Group group = groupRepository.findByName(groupMemberDto.getGroupName()).orElseThrow(
                () -> new Exception("Group does not exist")
        );

        User user = userRepository.findByUsername(groupMemberDto.getUsername()).orElseThrow(
                () -> new Exception("User not found")
        );

        if (groupMemberRepository.findByUserAndGroup(user, group).isPresent()) {
            throw new Exception("User already in group");
        }

        GroupMember groupMember = new GroupMember();
        groupMember.setGroup(group);
        groupMember.setUser(user);
        groupMember.setRole(groupMemberDto.getRole());

        groupMemberRepository.save(groupMember);
    }

    public void removeGroup(GroupRemoveDto groupRemoveDto) throws Exception {
        System.out.println(groupRemoveDto.getGroupName());
        Group group = groupRepository.findByName(groupRemoveDto.getGroupName()).orElseThrow(
                () -> new Exception("Group not found")
        );

        User user = userRepository.findByUsername(groupRemoveDto.getUsername()).orElseThrow(
                () -> new Exception("User not found")
        );

        GroupMember member = groupMemberRepository.findByUserAndGroup(user, group).orElseThrow(
                () -> new Exception("User not member of group")
        );

        if (member.getRole() != MemberRole.ADMIN) {
            throw new Exception("Not allowed to remove this group");
        }

        group.getMembers().forEach(groupMember -> System.out.println(groupMember.getUser().getUsername()));

        groupMemberRepository.deleteAll(group.getMembers());
        groupRepository.delete(group);
    }

    public void changeDescription(GroupDescriptionDto groupDescriptionDto) throws Exception {
        Group group = groupRepository.findByName(groupDescriptionDto.getGroupName()).orElseThrow(
                () -> new Exception("Group not found")
        );

        group.setDescription(groupDescriptionDto.getDescription());
        groupRepository.save(group);
    }
}
