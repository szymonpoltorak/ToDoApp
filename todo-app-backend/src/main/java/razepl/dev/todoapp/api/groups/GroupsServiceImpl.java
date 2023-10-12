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
import razepl.dev.todoapp.exceptions.groups.GroupDoesNotExistException;

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

        return groups
                .stream()
                .map(groupMapper::toGroupResponse)
                .toList();
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

    @Override
    public final GroupResponse deleteGroup(long groupId, User user) {
        log.info("Deleting group with id '{}' by user : {}", groupId, user.getUsername());

        Group group = findGroupInRepository(groupId, user);

        return groupMapper.toGroupResponse(group);
    }

    @Override
    public final GroupResponse editGroupsName(GroupResponse newGroupData, User user) {
        log.info("Editing group with id '{}' by user : {}", newGroupData.groupId(), user.getUsername());

        Group group = findGroupInRepository(newGroupData.groupId(), user);

        group.setGroupName(newGroupData.groupName());

        group = groupRepository.save(group);

        return groupMapper.toGroupResponse(group);
    }

    private Group findGroupInRepository(long groupId, User user) {
        Group group = groupRepository.findByGroupIdAndUser(groupId, user)
                .orElseThrow(() -> new GroupDoesNotExistException(
                        String.format("Group with id '%d' does not exist", groupId)
                ));
        log.info("Group found in db : {}", group);

        return group;
    }
}
