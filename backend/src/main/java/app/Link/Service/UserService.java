package app.Link.Service;

import app.Link.model.Contact;
import app.Link.model.User;
import app.Link.repository.BlacklistRepository;
import app.Link.repository.ContactRepository;
import app.Link.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;
    private final ContactRepository contactRepository;

    public Contact findContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    public void addContact(User user, User contact) {
        Contact newContact = Contact.builder()
                .id(contact.getId())
                .sender(user)
                .receiver(contact)
                .build();
        contactRepository.save(newContact);

        user.getSendersContacts().add(newContact);
        contact.getReceiversContacts().add(newContact);
    }

    public void removeContact(User user, User contact) throws NullPointerException {
        Contact contactToRemove = this.findContactById(contact.getId());
        if (contactToRemove == null) {
            throw new NullPointerException("Contact not found while deleting.");
        }
        contactRepository.delete(contactToRemove);

        user.getSendersContacts().remove(contactToRemove);
        contact.getReceiversContacts().remove(contactToRemove);
    }
}
