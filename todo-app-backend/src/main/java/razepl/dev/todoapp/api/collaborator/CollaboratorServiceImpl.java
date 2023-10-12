package razepl.dev.todoapp.api.collaborator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorMapper;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorService;
import razepl.dev.todoapp.entities.collaborator.Collaborator;
import razepl.dev.todoapp.entities.collaborator.interfaces.CollaboratorRepository;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.task.interfaces.TaskRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.exceptions.tasks.TaskDoesNotExistException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollaboratorServiceImpl implements CollaboratorService {
    private final CollaboratorRepository collaboratorRepository;
    private final TaskRepository taskRepository;
    private final CollaboratorMapper collaboratorMapper;

    @Override
    public final List<CollaboratorResponse> getListOfCollaborators(User user) {
        List<Collaborator> collaborators = collaboratorRepository.findCollaboratorsByUser(user);

        log.info("Finding collaborators for User : {}", user.getUsername());
        log.info("Found '{}' collaborators", collaborators.size());

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
}
