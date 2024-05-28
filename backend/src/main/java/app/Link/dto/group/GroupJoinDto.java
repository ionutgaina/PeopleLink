package app.Link.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupJoinDto {
    private String groupName;
    private String userName;
}
