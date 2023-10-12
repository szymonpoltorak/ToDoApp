package razepl.dev.todoapp.entities.collaborator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import razepl.dev.todoapp.entities.user.User;

import static razepl.dev.todoapp.entities.collaborator.constants.CollaboratorConstants.COLLABORATORS_TABLE_NAME;
import static razepl.dev.todoapp.entities.task.constants.TaskConstants.USER_ID_COLUMN_NAME;

@Data
@Entity
@Builder
@Table(name = COLLABORATORS_TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Collaborator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long collaboratorId;

    private String name;

    private String surname;

    @Column(unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME)
    private User user;
}
