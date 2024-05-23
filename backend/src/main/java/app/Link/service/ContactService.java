package app.Link.service;

import app.Link.common.ContactStatus;
import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.user.UserGetDto;
import app.Link.model.Contact;
import app.Link.model.User;
import app.Link.repository.ContactRepository;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public void addContact(ContactAddDto contactToAdd) throws Exception {
        User sender = userRepository.findByUsername(contactToAdd.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToAdd.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = new Contact();
        contact.setSender(sender);
        contact.setReceiver(receiver);
        contactRepository.save(contact);
    }

    public void acceptContact(ContactAddDto contactToAccept) throws Exception {
        User sender = userRepository.findByUsername(contactToAccept.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToAccept.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver);
        contact.setStatus(ContactStatus.ACCEPTED);
        contactRepository.save(contact);
    }

    public void rejectContact(ContactAddDto contactToAccept) throws Exception {
        User sender = userRepository.findByUsername(contactToAccept.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToAccept.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver);
        contact.setStatus(ContactStatus.REJECTED);
        contactRepository.save(contact);
    }

    public List<ContactAddDto> getPendingContacts(UserGetDto user) throws Exception {
        User receiver = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new Exception("User not found")
        );

        return contactRepository.findByReceiverAndStatus(receiver, ContactStatus.PENDING)
                .stream()
                .map(
                        contact -> new ContactAddDto(
                                contact.getSender().getUsername(),
                                contact.getReceiver().getUsername()
                        )
                )
                .toList();
    }

    public List<ContactAddDto> getSentRequests(UserGetDto user) throws Exception {
        User sender = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new Exception("User not found")
        );

        return contactRepository.findBySenderAndStatus(sender, ContactStatus.PENDING)
                .stream()
                .map(
                        contact -> new ContactAddDto(
                                contact.getSender().getUsername(),
                                contact.getReceiver().getUsername()
                        )
                )
                .toList();
    }

    public void cancelRequest(ContactAddDto contactToCancel) throws Exception {
        User sender = userRepository.findByUsername(contactToCancel.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToCancel.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver);
        contactRepository.delete(contact);
    }
}
