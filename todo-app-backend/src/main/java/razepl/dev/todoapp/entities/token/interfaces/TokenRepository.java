package razepl.dev.todoapp.entities.token.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.token.JwtToken;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<JwtToken, Long> {
    Optional<JwtToken> findByToken(String jwtToken);

    @Query("""
                select t from JwtToken as t
                inner join User as u on (t.user.userId = u.userId)
                where u.userId = :id and (t.isExpired = false or t.isRevoked = false)
            """)
    List<JwtToken> findAllValidTokensByUserId(@Param("id") Long id);

    void deleteAllByUser(User user);
}
