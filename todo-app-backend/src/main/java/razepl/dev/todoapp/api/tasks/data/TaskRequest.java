package razepl.dev.todoapp.api.tasks.data;

import lombok.Builder;

@Builder
public record TaskRequest(String title, String description, int priority, String dueDate) {
}
