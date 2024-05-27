package app.Link.service;

import app.Link.common.ContactStatus;
import app.Link.dto.contact.ContactAddDto;
import app.Link.dto.contact.ContactSendDto;
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

        if (contactRepository.findBySenderAndReceiver(sender, receiver).isPresent()) {
            throw new Exception("Friend request already sent");
        }

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

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found"));
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

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found"));
        contact.setStatus(ContactStatus.REJECTED);
        contactRepository.save(contact);
    }

    public List<ContactAddDto> getPendingContacts(String user) throws Exception {
        User receiver = userRepository.findByUsername(user).orElseThrow(
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

    public List<ContactAddDto> getSentRequests(String user) throws Exception {
        User sender = userRepository.findByUsername(user).orElseThrow(
                () -> new Exception("User not found")
        );

        return contactRepository.findBySenderAndStatus(sender, ContactStatus.PENDING)
                .stream()
                .map(
                        contact -> new ContactAddDto(
                                contact.getSender().getUsername(),
                                contact.getReceiver().getUsername()
                        )
                ).toList();
    }

    public void cancelRequest(ContactAddDto contactToCancel) throws Exception {
        User sender = userRepository.findByUsername(contactToCancel.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToCancel.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found"));
        contactRepository.delete(contact);
    }

    public List<ContactSendDto> getContacts(String username) throws Exception {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new Exception("User not found")
        );

        return contactRepository.findBySenderOrReceiver(user, user)
                .stream().map(
                        contact -> new ContactSendDto(
                                contact.getSender().getUsername(),
                                contact.getReceiver().getUsername(),
                                contact.getStatus().toString()
                        )
                ).toList();
    }

    public void blockContact(ContactAddDto contactToBlock) throws Exception {
        User sender = userRepository.findByUsername(contactToBlock.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contactToBlock.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contact = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found")
        );

        if (contact.getStatus() == ContactStatus.BLOCKED) {
            throw new Exception("Contact already blocked");
        }

        if (contact.getStatus() != ContactStatus.ACCEPTED) {
            throw new Exception("Contact not a friend");
        }

        contact.setStatus(ContactStatus.BLOCKED);
        contactRepository.save(contact);
    }

    public void unblockContact(ContactAddDto contact) throws Exception {
        User sender = userRepository.findByUsername(contact.getSender()).orElseThrow(
                () -> new Exception("Sender not found")
        );

        User receiver = userRepository.findByUsername(contact.getReceiver()).orElseThrow(
                () -> new Exception("Receiver not found")
        );

        Contact contactToUnblock = contactRepository.findBySenderAndReceiver(sender, receiver).orElseThrow(
                () -> new Exception("Contact not found")
        );

        if (contactToUnblock.getStatus() != ContactStatus.BLOCKED) {
            throw new Exception("Contact not blocked");
        }

        contactRepository.delete(contactToUnblock);
    }
}
