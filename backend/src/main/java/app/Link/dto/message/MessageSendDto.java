package app.Link.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageSendDto {
    private String text;
    private String senderName;
    private String contactSenderName;
    private String contactReceiverName;
}
