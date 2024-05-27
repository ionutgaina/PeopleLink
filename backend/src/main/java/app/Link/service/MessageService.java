package app.Link.service;

import app.Link.common.ContactStatus;
import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.message.MessageDto;
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
        User sender = userRepository.findByUsername(messageDto.getSenderName()).orElseThrow(
                () -> new Exception("User not found")
        );

        User contactSender = userRepository.findByUsername(messageDto.getContactSenderName()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        User contactReceiver = userRepository.findByUsername(messageDto.getContactReceiverName()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(contactSender, contactReceiver).orElseThrow(
                () -> new Exception("Contact not found")
        );

        if (contact.getStatus() != ContactStatus.ACCEPTED) {
            throw new Exception("Contact not accepted");
        }

        Message message = new Message();
        message.setContact(contact);
        message.setUser(sender);
        message.setText(messageDto.getText());

        messageRepository.save(message);
    }

    public List<MessageDto> getContactMessages(ContactAddDto contactDto) throws Exception {
        User sender = userRepository.findByUsername(contactDto.getSender()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        User receiver = userRepository.findByUsername(contactDto.getReceiver()).orElseThrow(
                () -> new Exception("Contact not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found")
        );

        return contact.getMessages().stream().map(
                m -> new MessageDto(m.getText(),
                                    m.getUser().getUsername(),
                                    m.getContact().getSender().getUsername(),
                                    m.getContact().getReceiver().getUsername())
        ).toList();
    }
}
