package app.Link.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GroupDto {
    private String ownerName;
    private String groupName;
    private String description;
    private List<String> members;
}
