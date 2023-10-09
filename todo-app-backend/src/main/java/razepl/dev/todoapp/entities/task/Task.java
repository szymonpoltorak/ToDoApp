package razepl.dev.todoapp.entities.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import razepl.dev.todoapp.api.tasks.data.TaskResponse;
import razepl.dev.todoapp.api.tasks.data.TaskUpdate;
import razepl.dev.todoapp.entities.task.interfaces.Updatable;
import razepl.dev.todoapp.entities.user.User;

import java.time.LocalDate;

import static razepl.dev.todoapp.entities.task.constants.TaskConstants.TASK_TABLE_NAME;
import static razepl.dev.todoapp.entities.task.constants.TaskConstants.USER_ID_COLUMN_NAME;
import static razepl.dev.todoapp.entities.task.constants.TaskConstraints.MAX_PRIORITY;
import static razepl.dev.todoapp.entities.task.constants.TaskConstraints.MIN_PRIORITY;
import static razepl.dev.todoapp.entities.task.constants.TaskMessages.MAX_PRIORITY_MESSAGE;
import static razepl.dev.todoapp.entities.task.constants.TaskMessages.MIN_PRIORITY_MESSAGE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = TASK_TABLE_NAME)
public class Task implements Updatable<TaskUpdate> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long taskId;

    @Column(unique = true)
    private String title;

    private String description;

    private LocalDate dueDate;

    private boolean isCompleted;

    @Min(value = MIN_PRIORITY, message = MIN_PRIORITY_MESSAGE)
    @Max(value = MAX_PRIORITY, message = MAX_PRIORITY_MESSAGE)
    private int priority;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME)
    private User user;

    @Override
    public final void update(TaskUpdate updateData) {
        this.title = updateData.title();
        this.description = updateData.description();
        this.dueDate = LocalDate.parse(updateData.dueDate());
        this.priority = updateData.priority();
    }
}
