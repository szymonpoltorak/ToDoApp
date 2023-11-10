package razepl.dev.todoapp.utils;

import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.data.TaskUpdate;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.user.User;

public record TaskTestData(Task newTask, User noteAuthor, TaskRequest taskRequest, TaskResponse taskResponse,
                           TaskUpdate taskUpdate) {
}
