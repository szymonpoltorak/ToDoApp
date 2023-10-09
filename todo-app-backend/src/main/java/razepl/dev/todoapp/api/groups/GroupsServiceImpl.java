package razepl.dev.todoapp.api.groups;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.groups.data.GroupsResponse;
import razepl.dev.todoapp.api.groups.interfaces.GroupsService;
import razepl.dev.todoapp.entities.groups.Group;
import razepl.dev.todoapp.entities.groups.interfaces.GroupMapper;
import razepl.dev.todoapp.entities.groups.interfaces.GroupRepository;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupsService {
    private static final int PAGE_SIZE = 10;
    private static final int PAGE_NUMBER = 0;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public final List<GroupsResponse> getListOfGroups(User user) {
        Pageable pageable = PageRequest.of(PAGE_SIZE, PAGE_NUMBER);
        Page<Group> groups = groupRepository.findGroupsByUserOrderByName(user, pageable);

        log.info("Finding groups for user : {}", user.getUsername());
        log.info("Found '{}' groups", groups.getTotalElements());

        return groups
                .stream()
                .map(groupMapper::toGroupsResponse)
                .toList();
    }
}
