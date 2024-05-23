package app.Link.repository;

import app.Link.common.ContactStatus;
import app.Link.model.Contact;
import app.Link.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByReceiver(User receiver);

    Contact findBySenderAndReceiver(User sender, User receiver);

    List<Contact> findByReceiverAndStatus(User receiver, ContactStatus contactStatus);

    List<Contact> findBySenderAndStatus(User sender, ContactStatus contactStatus);
}
