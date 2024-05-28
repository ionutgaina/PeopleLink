package app.Link.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupCreateDto {
    private String ownerName;
    private String groupName;
    private String description;
}
