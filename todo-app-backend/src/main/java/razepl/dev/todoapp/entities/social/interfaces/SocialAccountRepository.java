package razepl.dev.todoapp.entities.social.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import razepl.dev.todoapp.entities.social.SocialAccount;
import razepl.dev.todoapp.entities.social.SocialPlatform;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
    List<SocialAccount> findAllByUser(User user);

    Optional<SocialAccount> findBySocialAccountIdAndUser(long socialAccountId, User user);

    boolean existsByUserAndSocialPlatform(User user, SocialPlatform socialPlatform);
}
