package razepl.dev.todoapp.entities.groups;

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

import static razepl.dev.todoapp.entities.groups.constants.GroupConstants.GROUP_TABLE_NAME;
import static razepl.dev.todoapp.entities.task.constants.TaskConstants.USER_ID_COLUMN_NAME;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = GROUP_TABLE_NAME)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long groupId;

    @Column(unique = true)
    private String groupName;

    @ManyToOne
    @JoinColumn(name = USER_ID_COLUMN_NAME)
    private User user;
}
