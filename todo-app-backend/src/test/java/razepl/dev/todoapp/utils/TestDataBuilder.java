package razepl.dev.todoapp.utils;

import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.data.TaskUpdate;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.user.User;

public class TestDataBuilder {
    private TestDataBuilder() {
    }

    public static TaskTestData buildNoteTestData() {
        String content = "content";
        String title = "title";
        long taskId = 1L;

        Task newTask = Task
                .builder()
                .description(content)
                .title(title)
                .taskId(taskId)
                .build();
        User user = User
                .builder()
                .username("author@gmail.com")
                .build();
        TaskRequest taskRequest = TaskRequest
                .builder()
                .description(content)
                .dueDate("2021-01-01")
                .title(title)
                .build();
        TaskResponse taskResponse = TaskResponse
                .builder()
                .description(content)
                .title(title)
                .dueDate("2021-01-01")
                .taskId(taskId)
                .build();
        TaskUpdate taskUpdate = TaskUpdate
                .builder()
                .taskId(newTask.getTaskId())
                .dueDate("2021-01-01")
                .description("New note content")
                .title("New note title")
                .build();
        return new TaskTestData(newTask, user, taskRequest, taskResponse, taskUpdate);
    }
}
