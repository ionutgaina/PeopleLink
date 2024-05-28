package app.Link.dto.groupMessage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GroupMessageDto {
    private String text;
    private String senderName;
    private LocalDateTime timestamp;
}
