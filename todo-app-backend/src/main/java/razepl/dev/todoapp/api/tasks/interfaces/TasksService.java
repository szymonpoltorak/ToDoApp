package razepl.dev.todoapp.api.tasks.interfaces;

import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.data.TaskUpdate;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface TasksService {
    TaskResponse createNewTask(TaskRequest taskRequest, User taskAuthor);

    List<TaskResponse> getTasksFromPage(int pageNumber, boolean isCompleted, long groupId, User taskAuthor);

    TaskResponse deleteTask(long taskId);

    TaskResponse updateTask(TaskUpdate updateData);

    TaskResponse updateTaskCompletionStatus(long taskId);
}
