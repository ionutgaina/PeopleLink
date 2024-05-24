package app.Link.dto.group;

import lombok.Data;

@Data
public class GroupRemoveUserDto {
    private String groupName;
    private String username;
    private String removeUsername;
}
