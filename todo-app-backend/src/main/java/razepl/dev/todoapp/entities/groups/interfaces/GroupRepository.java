package razepl.dev.todoapp.entities.groups.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.groups.Group;
import razepl.dev.todoapp.entities.user.User;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Page<Group> findGroupsByUserOrderByGroupName(User user, Pageable pageable);

    Optional<Group> findByGroupIdAndUser(long groupId, User user);

    @Query("SELECT g FROM Group g WHERE g.groupName = :groupName")
    Optional<Group> findByGroupName(@Param("groupName") String groupName);
}
