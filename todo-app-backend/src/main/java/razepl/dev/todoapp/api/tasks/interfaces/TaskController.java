package razepl.dev.todoapp.api.tasks.interfaces;

import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface TaskController {
    TaskResponse createNewTask(TaskRequest taskRequest, User user);

    List<TaskResponse> getTasksFromPage(int pageNumber, User user);

    TaskResponse deleteTask(long taskId);

    TaskResponse updateTask(TaskResponse updateData);
}
