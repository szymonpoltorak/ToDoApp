package razepl.dev.todoapp.entities.collaborator.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.collaborator.Collaborator;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {
}
