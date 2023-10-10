package razepl.dev.todoapp.api.tasks.data;

import lombok.Builder;

@Builder
public record TaskUpdate(long taskId, String description, String title, String dueDate, int priority) {
}
