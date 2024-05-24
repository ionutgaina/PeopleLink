package app.Link.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GroupRemoveDto {
    private String groupName;
    private String adminName;
}
