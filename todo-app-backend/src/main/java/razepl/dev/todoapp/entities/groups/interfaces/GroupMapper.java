package razepl.dev.todoapp.entities.groups.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.groups.data.GroupsResponse;
import razepl.dev.todoapp.entities.groups.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupsResponse toGroupsResponse(Group group);
}
