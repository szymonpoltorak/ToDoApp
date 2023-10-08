package razepl.dev.todoapp.entities.task.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.user.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Page<Task> findTasksByUserOrderByDueDateDesc(User user, Pageable pageable);
}
