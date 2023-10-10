package razepl.dev.todoapp.api.tasks;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.tasks.data.TaskRequest;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.data.TaskUpdate;
import razepl.dev.todoapp.api.tasks.interfaces.TaskMapper;
import razepl.dev.todoapp.api.tasks.interfaces.TasksService;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.task.interfaces.TaskRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.exceptions.tasks.TaskDoesNotExistException;

import java.time.LocalDate;
import java.util.List;

import static razepl.dev.todoapp.api.tasks.constants.Constants.PAGE_SIZE;

@Slf4j
@Service
@RequiredArgsConstructor
public class TasksServiceImpl implements TasksService {
    private static final String TASK_ERROR_MESSAGE = "Task of id '%s' does not exist!";
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public final TaskResponse createNewTask(TaskRequest taskRequest, User taskAuthor) {
        log.info("Received request with data : {}", taskRequest);
        log.info("Request is from user : {}", taskAuthor.getUsername());

        Task newTask = Task
                .builder()
                .title(taskRequest.title())
                .description(taskRequest.description())
                .dueDate(LocalDate.parse(taskRequest.dueDate()))
                .priority(taskRequest.priority())
                .user(taskAuthor)
                .build();
        Task repoTask = taskRepository.save(newTask);

        return taskMapper.toTaskResponse(repoTask);
    }

    @Override
    public final List<TaskResponse> getTasksFromPage(int pageNumber, User taskAuthor) {
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        Page<Task> tasks = taskRepository.findTasksByUserOrderByDueDateDesc(taskAuthor, pageable);

        log.info("Found '{}' tasks for user '{}' on page '{}'", tasks.getTotalElements(), taskAuthor.getUsername(), pageNumber);

        return tasks
                .stream()
                .map(taskMapper::toTaskResponse)
                .toList();
    }

    @Override
    public final TaskResponse deleteTask(long taskId) {
        Task taskToDelete = getTaskFromRepository(taskId);

        log.info("Deleting task : {}", taskToDelete);

        taskRepository.delete(taskToDelete);

        return taskMapper.toTaskResponse(taskToDelete);
    }

    @Override
    public final TaskResponse updateTask(TaskUpdate updateData) {
        log.info("Updating with data : {}", updateData);

        Task taskToUpdate = getTaskFromRepository(updateData.taskId());

        log.info("Task to be updated : {}", taskToUpdate);

        taskToUpdate.update(updateData);

        taskRepository.save(taskToUpdate);

        return taskMapper.toTaskResponse(taskToUpdate);
    }

    @Override
    public final TaskResponse updateTaskCompletionStatus(long taskId) {
        log.info("Task of id {} has been completed!", taskId);

        Task taskToUpdate = getTaskFromRepository(taskId);

        log.info("Was task completed ? : {}", taskToUpdate.isCompleted());

        taskToUpdate.setCompleted(!taskToUpdate.isCompleted());

        taskRepository.save(taskToUpdate);

        return taskMapper.toTaskResponse(taskToUpdate);
    }

    private Task getTaskFromRepository(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskDoesNotExistException(
                        String.format(TASK_ERROR_MESSAGE, taskId))
                );
    }
}
