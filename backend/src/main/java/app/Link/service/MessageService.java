package app.Link.service;

import app.Link.common.ContactStatus;
import app.Link.dto.message.MessageDto;
import app.Link.dto.message.MessageGetDto;
import app.Link.dto.message.MessageSendDto;
import app.Link.model.Contact;
import app.Link.model.Message;
import app.Link.model.User;
import app.Link.repository.ContactRepository;
import app.Link.repository.MessageRepository;
import app.Link.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;

    public void sendMessage(MessageSendDto messageDto) throws Exception {
        User messageSender = userRepository.findByUsername(messageDto.getSenderName()).orElseThrow(
                () -> new Exception("User not found")
        );

        Contact contact = contactRepository.findByRoomCode(messageDto.getRoomCode()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        if (contact.getStatus() != ContactStatus.ACCEPTED) {
            throw new Exception("Contact not accepted");
        }

        Message message = new Message();
        message.setContact(contact);
        message.setUser(messageSender);
        message.setText(messageDto.getText());
        message.setMediaUrl(messageDto.getMediaUrl());

        messageRepository.save(message);
    }

    public List<MessageDto> getContactMessages(MessageGetDto messageGetDto) throws Exception {
        Contact contact = contactRepository.findByRoomCode(messageGetDto.getRoomCode()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        return listMessages(contact);
    }

    List<MessageDto> listMessages(Contact contact) {
        return contact.getMessages().stream().map(
                message -> new MessageDto(
                        message.getText(),
                        message.getUser().getUsername(),
                        message.getMediaUrl(),
                        message.getTimestamp()
                )
        ).toList();
    }
}
