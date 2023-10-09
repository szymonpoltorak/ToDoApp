package razepl.dev.todoapp.api.groups;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.groups.data.GroupsResponse;
import razepl.dev.todoapp.api.groups.interfaces.GroupsController;
import razepl.dev.todoapp.api.groups.interfaces.GroupsService;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.GET_LIST_OF_GROUPS_MAPPING;
import static razepl.dev.todoapp.api.groups.constants.GroupsMappings.GROUPS_MAPPING;

@RestController
@RequestMapping(name = GROUPS_MAPPING)
@RequiredArgsConstructor
public class GroupsControllerImpl implements GroupsController {
    private final GroupsService groupsService;

    @Override
    @GetMapping(value = GET_LIST_OF_GROUPS_MAPPING)
    public final List<GroupsResponse> getListOfGroups(@AuthenticationPrincipal User user) {
        return groupsService.getListOfGroups(user);
    }
}
