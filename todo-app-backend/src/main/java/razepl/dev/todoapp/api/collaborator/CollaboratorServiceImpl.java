package razepl.dev.todoapp.api.collaborator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorRequest;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorSuggestion;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorMapper;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorService;
import razepl.dev.todoapp.entities.collaborator.Collaborator;
import razepl.dev.todoapp.entities.collaborator.interfaces.CollaboratorRepository;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.task.interfaces.TaskRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;
import razepl.dev.todoapp.exceptions.tasks.TaskDoesNotExistException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {
    private static final String FOUND_COLLABORATORS = "Found '{}' collaborators";
    private static final String FOUND_COLLABORATOR = "Found collaborator : {}";
    private final CollaboratorRepository collaboratorRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CollaboratorMapper collaboratorMapper;

    @Override
    public final List<CollaboratorResponse> getListOfCollaborators(User user) {
        List<Collaborator> collaborators = collaboratorRepository.findCollaboratorsByUser(user);

        log.info("Finding collaborators for User : {}", user.getUsername());
        log.info(FOUND_COLLABORATORS, collaborators.size());

        return collaborators
                .stream()
                .map(collaboratorMapper::toCollaboratorResponse)
                .toList();
    }

    @Override
    public final List<CollaboratorResponse> getCollaboratorsAssignedToTask(long taskId, User user) {
        log.info("Getting collaborators associated with task of id '{}' by the user '{}'", taskId, user.getUsername());

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskDoesNotExistException("Task does not exist!"));

        log.info("Task that was found : {}", task);

        return task
                .getCollaborator()
                .stream()
                .map(collaboratorMapper::toCollaboratorResponse)
                .toList();
    }

    @Override
    public final CollaboratorResponse addUserAsCollaborator(String collaboratorUsername, User user) {
        if (collaboratorUsername.equals(user.getUsername())) {
            log.error("Username : {}", collaboratorUsername);

            throw new UsernameNotFoundException("User cannot add himself as collaborator!");
        }
        log.info("Adding user : {} as collaborator for user : {}", collaboratorUsername, user.getUsername());

        User collaboratorUser = userRepository.findByUsername(collaboratorUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        Collaborator collaborator = Collaborator
                .builder()
                .fullName(collaboratorUser.getFullName())
                .username(collaboratorUsername)
                .user(user)
                .build();
        collaborator = collaboratorRepository.save(collaborator);

        log.info("Mapping to respone collaborator: {}", collaborator);

        return collaboratorMapper.toCollaboratorResponse(collaborator);
    }

    @Override
    public final CollaboratorResponse assignCollaboratorToTask(CollaboratorRequest collaboratorRequest) {
        log.info("Received request : {}", collaboratorRequest);

        Task task = taskRepository.findById(collaboratorRequest.taskId())
                .orElseThrow(() -> new TaskDoesNotExistException("Task has not been found in repository!"));
        log.info("Found task : {}", task);

        Collaborator collaborator = collaboratorRepository.findByUsername(collaboratorRequest.collaboratorUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Collaborator does not exist!"));
        log.info(FOUND_COLLABORATOR, collaborator);
        log.info("Number of collaborators before : {}", task.getCollaborator().size());

        task.getCollaborator().add(collaborator);

        task = taskRepository.save(task);

        log.info("Number of collaborators after : {}", task.getCollaborator().size());

        return collaboratorMapper.toCollaboratorResponse(collaborator);
    }

    @Override
    public final List<CollaboratorSuggestion> findCollaboratorsByPattern(String searchPattern) {
        log.info("Searching for collaborators by pattern : {}", searchPattern);

        List<User> users = userRepository.findUsersByFullNameContaining(searchPattern);

        log.info("Found '{}' users", users.size());

        return users
                .stream()
                .map(collaboratorMapper::toCollaboratorSuggestion)
                .toList();
    }

    @Override
    public final CollaboratorResponse removeUserFromCollaborators(long collaboratorId) {
        log.info("Removing collaborator of id : {}", collaboratorId);

        Collaborator collaborator = collaboratorRepository.findById(collaboratorId)
                .orElseThrow(() -> new UsernameNotFoundException("Collaborator does not exist!"));
        log.info(FOUND_COLLABORATOR, collaborator);

        collaboratorRepository.deleteById(collaboratorId);

        return collaboratorMapper.toCollaboratorResponse(collaborator);
    }
}
