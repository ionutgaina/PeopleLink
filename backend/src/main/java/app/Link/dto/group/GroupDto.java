package app.Link.dto.group;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {
    private String owner;
    private String name;
    private String description;
    private List<String> members;
}
