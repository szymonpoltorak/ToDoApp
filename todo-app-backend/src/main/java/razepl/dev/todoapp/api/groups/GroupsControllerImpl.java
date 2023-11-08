package razepl.dev.todoapp.api.groups;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.groups.data.GroupResponse;
import razepl.dev.todoapp.api.groups.interfaces.GroupsController;
import razepl.dev.todoapp.api.groups.interfaces.GroupsService;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.ADD_NEW_GROUP_MAPPING;
import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.DELETE_GROUP_MAPPING;
import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.EDIT_GROUP_NAME_MAPPING;
import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.GET_LIST_OF_GROUPS_MAPPING;
import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.GROUPS_MAPPING;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = GROUPS_MAPPING)
public class GroupsControllerImpl implements GroupsController {
    private final GroupsService groupsService;

    @Override
    @GetMapping(value = GET_LIST_OF_GROUPS_MAPPING)
    public final List<GroupResponse> getListOfGroups(@RequestParam int numOfPage,
                                                     @AuthenticationPrincipal User user) {
        return groupsService.getListOfGroups(numOfPage, user);
    }

    @Override
    @PostMapping(value = ADD_NEW_GROUP_MAPPING)
    public final GroupResponse addNewGroup(@RequestParam String groupName,
                                           @AuthenticationPrincipal User user) {
        return groupsService.addNewGroup(groupName, user);
    }

    @Override
    @DeleteMapping(value = DELETE_GROUP_MAPPING)
    public final GroupResponse deleteGroup(@RequestParam long groupId,
                                           @AuthenticationPrincipal User user) {
        return groupsService.deleteGroup(groupId, user);
    }

    @Override
    @PatchMapping(value = EDIT_GROUP_NAME_MAPPING)
    public final GroupResponse editGroupsName(@RequestBody GroupResponse newGroupData,
                                              @AuthenticationPrincipal User user) {
        return groupsService.editGroupsName(newGroupData, user);
    }
}
