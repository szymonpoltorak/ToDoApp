package razepl.dev.todoapp.api.tasks.data;

import lombok.Builder;

@Builder
public record TaskResponse(long taskId, String description, String title,
                           String dueDate, int priority, boolean isCompleted) {
}
