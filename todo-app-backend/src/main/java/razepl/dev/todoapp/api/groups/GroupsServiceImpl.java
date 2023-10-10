package razepl.dev.todoapp.api.groups;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.groups.data.GroupResponse;
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
    public final List<GroupResponse> getListOfGroups(User user) {
        Pageable pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        Page<Group> groups = groupRepository.findGroupsByUserOrderByGroupName(user, pageable);

        log.info("Finding groups for user : {}", user.getUsername());
        log.info("Found '{}' groups", groups.getTotalElements());

        log.info(groups.toString());

        var l = groups
                .stream()
                .map(groupMapper::toGroupResponse)
                .toList();
        log.info(l.toString());

        return l;
    }

    @Override
    public final GroupResponse addNewGroup(String groupName, User user) {
        log.info("Adding new group '{}' for user : {}", groupName, user.getUsername());

        Group group = Group
                .builder()
                .groupName(groupName)
                .user(user)
                .build();
        group = groupRepository.save(group);

        return groupMapper.toGroupResponse(group);
    }
}
