package razepl.dev.todoapp.api.tasks;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.interfaces.TaskMapper;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.task.interfaces.TaskRepository;
import razepl.dev.todoapp.utils.TaskTestData;
import razepl.dev.todoapp.utils.TestDataBuilder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static razepl.dev.todoapp.api.tasks.constants.Constants.PAGE_SIZE;

@SpringBootTest
class TasksServiceTest {
    private final TaskTestData testData = TestDataBuilder.buildNoteTestData();
    @InjectMocks
    private TasksServiceImpl tasksService;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;

    @Test
    final void test_createNewTask_shouldCreateTask() {
        // given
        when(taskRepository.save(any(Task.class)))
                .thenReturn(testData.newTask());

        when(taskMapper.toTaskResponse(testData.newTask()))
                .thenReturn(testData.taskResponse());

        // when
        TaskResponse actual = tasksService.createNewTask(testData.taskRequest(), testData.noteAuthor());

        // then
        assertEquals(testData.taskResponse(), actual, "The created note differs from the note returned by service");
    }

    @Test
    final void test_getTasksFromPage_shouldCorrectlyReturnTask() {
        // given
        final int pageNumber = 0;
        final long groupId = 1L;
        boolean isCompleted = true;
        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
        List<TaskResponse> expected = List.of(testData.taskResponse());

        when(taskRepository.findTasksByUserAndIsCompleted(testData.noteAuthor(), isCompleted,
                groupId, pageable))
                .thenReturn(new PageImpl<>(List.of(testData.newTask())));
        when(taskMapper.toTaskResponse(testData.newTask()))
                .thenReturn(testData.taskResponse());

        // when
        List<TaskResponse> actual = tasksService.getTasksFromPage(pageNumber, isCompleted, groupId, testData.noteAuthor());

        // then
        assertEquals(expected, actual, "List of notes for the user differed from the expected");
    }

    @Test
    final void test_deleteTask_shouldDeleteTask() {
        // given
        Task task = testData.newTask();

        when(taskRepository.findById(task.getTaskId()))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(testData.taskResponse());

        // when
        TaskResponse actual = tasksService.deleteTask(task.getTaskId());

        // then
        assertEquals(testData.taskResponse(), actual, "The deleted tasks differs from expected");
        verify(taskRepository).delete(task);
    }

    @Test
    final void test_updateTask_shouldUpdateTask() {
        // given
        Task task = testData.newTask();
        TaskResponse expected = TaskResponse
                .builder()
                .taskId(task.getTaskId())
                .description("New note content")
                .title("New note title")
                .build();

        when(taskRepository.findById(task.getTaskId()))
                .thenReturn(Optional.of(task));
        when(taskMapper.toTaskResponse(task))
                .thenReturn(expected);

        // when
        TaskResponse actual = tasksService.updateTask(testData.taskUpdate());

        // then
        assertEquals(expected, actual, "The updatedNote differs from the expected");
        verify(taskRepository).save(any(Task.class));
    }
}
