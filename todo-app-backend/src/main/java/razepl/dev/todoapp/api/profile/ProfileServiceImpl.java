package razepl.dev.todoapp.api.profile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import razepl.dev.todoapp.api.profile.data.SocialAccountRequest;
import razepl.dev.todoapp.api.profile.data.SocialAccountResponse;
import razepl.dev.todoapp.api.profile.data.UserResponse;
import razepl.dev.todoapp.api.profile.interfaces.ProfileService;
import razepl.dev.todoapp.api.profile.interfaces.UserMapper;
import razepl.dev.todoapp.entities.social.SocialAccount;
import razepl.dev.todoapp.entities.social.interfaces.SocialAccountMapper;
import razepl.dev.todoapp.entities.social.interfaces.SocialAccountRepository;
import razepl.dev.todoapp.entities.token.interfaces.TokenRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;
import razepl.dev.todoapp.exceptions.profile.SocialAccountDoesNotExistException;
import razepl.dev.todoapp.exceptions.profile.SocialLinkDoesNotMatchPlatformException;
import razepl.dev.todoapp.exceptions.profile.SocialPlatformAlreadyAddedException;

import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final SocialAccountRepository socialAccountRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final SocialAccountMapper socialAccountMapper;

    @Override
    public UserResponse getUserData(User user) {
        log.info("Getting data from user : {}", user);

        List<SocialAccountResponse> socialAccounts =  socialAccountRepository
                .findAllByUser(user)
                .stream()
                .map(socialAccountMapper::toSocialAccountResponse)
                .toList();

        log.info("Found social accounts : {}", socialAccounts.size());

        return UserResponse
                .builder()
                .name(user.getName())
                .surname(user.getSurname())
                .username(user.getUsername())
                .socialAccounts(socialAccounts)
                .build();
    }

    @Override
    public UserResponse closeUsersAccount(User user) {
        log.info("Closing account of user : {}", user);

        tokenRepository.deleteAllByUser(user);

        log.info("Deleted all user tokens.");

        userRepository.delete(user);

        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse addNewSocialAccount(SocialAccountRequest socialAccountRequest, User user) {
        log.info("Adding new social account : {} to user : {}", socialAccountRequest, user);

        SocialAccount socialAccount = SocialAccount
                .builder()
                .socialName(socialAccountRequest.socialName())
                .socialPlatform(socialAccountRequest.socialPlatform())
                .socialLink(socialAccountRequest.socialLink())
                .user(user)
                .build();
        log.info("Created social account : {}", socialAccount);

        if (!socialAccount.getSocialLink().contains(socialAccount.getSocialPlatform().toString().toLowerCase())) {
            throw new SocialLinkDoesNotMatchPlatformException("Link should contain platform name!");
        }
        if (socialAccountRepository.existsByUserAndSocialPlatform(user, socialAccount.getSocialPlatform())) {
            throw new SocialPlatformAlreadyAddedException("You already have this account linked!");
        }
        socialAccountRepository.save(socialAccount);

        return getUserData(user);
    }

    @Override
    public final UserResponse removeSocialAccount(long socialAccountId, User user) {
        log.info("Removing social account : {} from user : {}", socialAccountId, user);

        SocialAccount socialAccount = socialAccountRepository.findBySocialAccountIdAndUser(socialAccountId, user)
                .orElseThrow(() -> new SocialAccountDoesNotExistException("Such social account does not exist!"));

        log.info("Found social account : {}", socialAccount);

        socialAccountRepository.deleteById(socialAccountId);

        return getUserData(user);
    }
}
