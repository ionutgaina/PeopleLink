package app.Link.dto.groupMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageSendDto {
    private String senderName;
    private String text;
    private String groupName;
}
