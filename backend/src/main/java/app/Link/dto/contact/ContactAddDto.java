package app.Link.dto.contact;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ContactAddDto {
    private String sender;
    private String receiver;
}
