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

        int index = messageDto.getRoomCode().indexOf(messageDto.getSenderName());
        String contactUserName;

        Contact contact;

        if (index == 0) {
            contactUserName = messageDto.getRoomCode().substring(messageDto.getSenderName().length() + 1);

            User receiverUser = userRepository.findByUsername(contactUserName).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            contact = contactRepository.findBySenderAndReceiver(messageSender, receiverUser).orElseThrow(
                    () -> new Exception("Contact not found")
            );
        } else {
            contactUserName = messageDto.getRoomCode().substring(0, index - 1);

            User senderUser = userRepository.findByUsername(contactUserName).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            contact = contactRepository.findBySenderAndReceiver(senderUser, messageSender).orElseThrow(
                    () -> new Exception("Contact not found")
            );
        }

        if (contact.getStatus() != ContactStatus.ACCEPTED) {
            throw new Exception("Contact not accepted");
        }

        Message message = new Message();
        message.setContact(contact);
        message.setUser(messageSender);
        message.setText(messageDto.getText());

        messageRepository.save(message);
    }

    public List<MessageDto> getContactMessages(MessageGetDto messageGetDto) throws Exception {
        User user = userRepository.findByUsername(messageGetDto.getUserName()).orElseThrow(
                () -> new Exception("User not found")
        );

        String senderName;
        String receiverName;

        int index = messageGetDto.getRoomCode().indexOf(messageGetDto.getUserName());
        if (index == 0) {
            receiverName = messageGetDto.getRoomCode().substring(messageGetDto.getUserName().length() + 1);
            User receiverUser = userRepository.findByUsername(receiverName).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            Contact contact = contactRepository.findBySenderAndReceiver(user, receiverUser).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            return listMessages(contact);
        } else {
            senderName = messageGetDto.getUserName().substring(0, index - 1);
            User senderUser = userRepository.findByUsername(senderName).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            Contact contact = contactRepository.findBySenderAndReceiver(senderUser, user).orElseThrow(
                    () -> new Exception("Contact not found")
            );

            return listMessages(contact);
        }
    }

    List<MessageDto> listMessages(Contact contact) {
        return contact.getMessages().stream().map(
                message -> new MessageDto(message.getText(), message.getUser().getUsername())
        ).toList();
    }
}
