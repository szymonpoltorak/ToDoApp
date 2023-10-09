package razepl.dev.todoapp.entities.groups;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static razepl.dev.todoapp.entities.groups.constants.GroupConstants.GROUP_TABLE_NAME;

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

    private String name;
}
