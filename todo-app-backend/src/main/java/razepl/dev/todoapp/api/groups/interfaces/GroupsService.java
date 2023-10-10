package razepl.dev.todoapp.api.groups.interfaces;

import razepl.dev.todoapp.api.groups.data.GroupResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface GroupsService {
    List<GroupResponse> getListOfGroups(User user);
    GroupResponse addNewGroup(String groupName, User user);

    GroupResponse deleteGroup(long groupId, User user);
}
