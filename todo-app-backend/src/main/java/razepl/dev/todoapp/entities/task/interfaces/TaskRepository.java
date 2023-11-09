package razepl.dev.todoapp.entities.task.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.task.Task;
import razepl.dev.todoapp.entities.user.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("""
        SELECT t FROM Task t
        WHERE t.user = :user and t.isCompleted = :isCompleted and t.group.groupId = :groupId
        ORDER BY t.priority DESC
    """)
    Page<Task> findTasksByUserAndIsCompleted(@Param("user") User user, @Param("isCompleted") boolean isCompleted,
                                             @Param("groupId") long groupId, Pageable pageable);
}
