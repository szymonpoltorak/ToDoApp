package razepl.dev.todoapp.entities.groups.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.groups.data.GroupResponse;
import razepl.dev.todoapp.entities.groups.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {
    GroupResponse toGroupResponse(Group group);
}
