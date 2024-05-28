package app.Link.model;

import app.Link.common.ContactStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ContactStatus status = ContactStatus.PENDING;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "room_code")
    private String roomCode;
}
