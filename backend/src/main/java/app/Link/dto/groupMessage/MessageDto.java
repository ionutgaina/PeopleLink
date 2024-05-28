package app.Link.dto.groupMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MessageDto {
    private String text;
    private String senderName;
    private LocalDateTime timestamp;
}
