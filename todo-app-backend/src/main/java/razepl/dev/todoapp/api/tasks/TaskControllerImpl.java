package razepl.dev.todoapp.api.tasks;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.interfaces.TaskController;
import razepl.dev.todoapp.api.tasks.interfaces.TasksService;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

import static razepl.dev.todoapp.api.tasks.constants.TasksMappings.CREATE_TASK_MAPPING;
import static razepl.dev.todoapp.api.tasks.constants.TasksMappings.DELETE_TASK_MAPPING;
import static razepl.dev.todoapp.api.tasks.constants.TasksMappings.GET_TASKS_MAPPING;
import static razepl.dev.todoapp.api.tasks.constants.TasksMappings.TASKS_ENDPOINTS_MAPPING;
import static razepl.dev.todoapp.api.tasks.constants.TasksMappings.UPDATE_TASK_MAPPING;

@RestController
@RequestMapping(value = TASKS_ENDPOINTS_MAPPING)
@RequiredArgsConstructor
public class TaskControllerImpl implements TaskController {
    private final TasksService tasksService;

    @Override
    @PostMapping(value = CREATE_TASK_MAPPING)
    public final TaskResponse createNewTask(@RequestBody TaskRequest taskRequest,
                                            @AuthenticationPrincipal User user) {
        return tasksService.createNewTask(taskRequest, user);
    }

    @Override
    @GetMapping(value = GET_TASKS_MAPPING)
    public final List<TaskResponse> getTasksFromPage(@RequestParam int pageNumber,
                                                     @AuthenticationPrincipal User user) {
        return tasksService.getTasksFromPage(pageNumber, user);
    }

    @Override
    @DeleteMapping(value = DELETE_TASK_MAPPING)
    public final TaskResponse deleteTask(@RequestParam long taskId) {
        return tasksService.deleteTask(taskId);
    }

    @Override
    @PatchMapping(value = UPDATE_TASK_MAPPING)
    public final TaskResponse updateTask(@RequestBody TaskResponse updateData) {
        return tasksService.updateTask(updateData);
    }
}
