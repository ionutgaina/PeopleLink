package app.Link.dto.groupMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDto {
    private String text;
    private String senderName;
}
