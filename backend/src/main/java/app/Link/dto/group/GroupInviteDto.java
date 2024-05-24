package app.Link.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupInviteDto {
    private String groupName;
    private String adminName;
    private String userName;
}