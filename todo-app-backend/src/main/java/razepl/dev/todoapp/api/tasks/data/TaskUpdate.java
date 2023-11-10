package razepl.dev.todoapp.api.tasks.data;

import lombok.Builder;

import java.util.List;

@Builder
public record TaskUpdate(long taskId, String description, String title, String dueDate, int priority,
                         List<String> collaboratorUsernames) {
}
