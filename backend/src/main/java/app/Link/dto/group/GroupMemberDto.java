package app.Link.dto.group;

import app.Link.common.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupMemberDto {
    private String groupName;
    private String userName;
    private MemberRole role;
}
