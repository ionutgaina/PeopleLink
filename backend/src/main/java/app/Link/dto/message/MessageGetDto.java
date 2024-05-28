package app.Link.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageGetDto {
    private String roomCode;
    private String userName;
}
