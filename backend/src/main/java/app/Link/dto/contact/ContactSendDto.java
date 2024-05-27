package app.Link.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContactSendDto {
    private String sender;
    private String receiver;
    private String status;
}