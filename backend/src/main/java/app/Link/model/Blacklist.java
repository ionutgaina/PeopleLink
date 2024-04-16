package app.Link.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "blacklists")
public class Blacklist {
    @Id
    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Id
    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;
}
