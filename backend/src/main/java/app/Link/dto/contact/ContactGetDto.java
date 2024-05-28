package app.Link.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContactGetDto {
    private String sender;
    private String receiver;
    private String status;
    private String roomCode;
}