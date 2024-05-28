package app.Link.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageSendDto {
    private String text;
    private String senderName;
    private String roomCode;
    private String mediaUrl;
}
