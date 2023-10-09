package razepl.dev.todoapp.api.groups.interfaces;

import razepl.dev.todoapp.api.groups.data.GroupsResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface GroupsController {
    List<GroupsResponse> getListOfGroups(User user);
}
