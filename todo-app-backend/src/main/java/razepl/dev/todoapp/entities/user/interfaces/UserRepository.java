package razepl.dev.todoapp.entities.user.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.user.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User as u inner join JwtToken as t on u.userId = t.user.userId where t.token = :authToken")
    Optional<User> findUserByToken(String authToken);
}
