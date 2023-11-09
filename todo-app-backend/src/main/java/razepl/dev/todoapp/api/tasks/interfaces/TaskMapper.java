package razepl.dev.todoapp.api.tasks.interfaces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.entities.task.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "completed", target = "isCompleted")
    TaskResponse toTaskResponse(Task task);
}
