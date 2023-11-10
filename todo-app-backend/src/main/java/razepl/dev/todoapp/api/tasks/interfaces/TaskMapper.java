package razepl.dev.todoapp.api.tasks.interfaces;

import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.entities.task.Task;

@FunctionalInterface
public interface TaskMapper {
    TaskResponse toTaskResponse(Task task);
}
