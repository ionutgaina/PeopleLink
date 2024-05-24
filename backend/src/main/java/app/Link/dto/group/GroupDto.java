package app.Link.dto.group;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {
    private String ownerName;
    private String groupName;
    private String description;
    private List<String> members;
}
