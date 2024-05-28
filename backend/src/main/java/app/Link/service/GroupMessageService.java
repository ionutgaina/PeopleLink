package app.Link.service;

import app.Link.dto.groupMember.GroupMemberDto;
import app.Link.dto.groupMessage.MessageSendDto;
import app.Link.dto.groupMessage.MessageDto;
import app.Link.model.Group;
import app.Link.model.GroupMember;
import app.Link.model.GroupMessage;
import app.Link.model.User;
import app.Link.repository.GroupMemberRepository;
import app.Link.repository.GroupMessageRepository;
import app.Link.repository.GroupRepository;
import app.Link.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GroupMessageService {
    private final GroupRepository groupRepository;
    private final GroupMessageRepository groupMessageRepository;
    private final UserRepository userRepository;
    private final GroupMemberRepository groupMemberRepository;

    public void sendMessage(MessageSendDto messageDto) throws Exception {
        Group group = groupRepository.findByName(messageDto.getGroupName()).orElseThrow(
                () -> new Exception("Group not found")
        );

        User sender = userRepository.findByUsername(messageDto.getSenderName()).orElseThrow(
                () -> new Exception("User not found")
        );

        if(groupMemberRepository.findByUserAndGroup(sender, group).isEmpty()) {
            throw new Exception("Group member not found");
        }

        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setText(messageDto.getText());
        groupMessage.setSender(sender);
        groupMessage.setGroup(group);

        groupMessageRepository.save(groupMessage);
    }


    public List<MessageDto> getGroupMessages(GroupMemberDto groupMemberDto) throws Exception {
        Group group = groupRepository.findByName(groupMemberDto.getGroupName()).orElseThrow(
                () -> new Exception("Group not found")
        );

        User user = userRepository.findByUsername(groupMemberDto.getMemberName()).orElseThrow(
                () -> new Exception("User not found")
        );

        if (groupMemberRepository.findByUserAndGroup(user, group).isEmpty())
            throw new Exception("User not in group");

        return group.getMessages().stream().map(
                m -> new MessageDto(m.getText(), m.getSender().getUsername(), m.getTimestamp())
        ).toList();
    }
}
