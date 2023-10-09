package razepl.dev.todoapp.entities.groups.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.groups.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
