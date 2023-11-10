package razepl.dev.todoapp.api.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorMapper;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.interfaces.TaskMapper;
import razepl.dev.todoapp.entities.task.Task;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapperImpl implements TaskMapper {
    private final CollaboratorMapper collaboratorMapper;

    @Override
    public final TaskResponse toTaskResponse(Task task) {
        if (task == null) {
            return null;
        }
        List<CollaboratorResponse> collaborators = task.getCollaborator()
                .stream()
                .map(collaboratorMapper::toCollaboratorResponse)
                .toList();

        return TaskResponse
                .builder()
                .isCompleted(task.isCompleted())
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .dueDate(task.getDueDate().toString())
                .priority(task.getPriority())
                .collaborators(collaborators)
                .description(task.getDescription())
                .build();
    }
}
