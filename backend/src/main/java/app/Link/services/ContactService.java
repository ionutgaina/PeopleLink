package app.Link.services;

import app.Link.model.User;
import app.Link.repository.BlacklistRepository;
import app.Link.repository.ContactRepository;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ContactService {
    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;
    private final ContactRepository contactRepository;

    public void addContact(User user, User contact) {
        app.Link.model.Contact newContact = app.Link.model.Contact.builder()
                .id(contact.getId())
                .sender(user)
                .receiver(contact)
                .build();
        contactRepository.save(newContact);

        user.getSendersContacts().add(newContact);
        contact.getReceiversContacts().add(newContact);
    }

    public void removeContact(User user, User contact) throws NullPointerException {
        app.Link.model.Contact contactToRemove = this.contactRepository.findById(contact.getId()).orElse(null);
        if (contactToRemove == null) {
                throw new NullPointerException("Contact not found while deleting.");
        }
        contactRepository.delete(contactToRemove);

        user.getSendersContacts().remove(contactToRemove);
        contact.getReceiversContacts().remove(contactToRemove);
    }
}
