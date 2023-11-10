package razepl.dev.todoapp.api.tasks.data;

import lombok.Builder;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;

import java.util.List;

@Builder
public record TaskResponse(long taskId, String description, String title, List<CollaboratorResponse> collaborators,
                           String dueDate, int priority, boolean isCompleted) {
}
