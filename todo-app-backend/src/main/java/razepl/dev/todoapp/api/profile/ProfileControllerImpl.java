package razepl.dev.todoapp.api.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.profile.data.SocialAccountRequest;
import razepl.dev.todoapp.api.profile.data.UserResponse;
import razepl.dev.todoapp.api.profile.interfaces.ProfileController;
import razepl.dev.todoapp.api.profile.interfaces.ProfileService;
import razepl.dev.todoapp.entities.user.User;

import static razepl.dev.todoapp.api.profile.constants.ProfileMappings.ADD_SOCIAL_ACCOUNT_MAPPING;
import static razepl.dev.todoapp.api.profile.constants.ProfileMappings.CLOSE_ACCOUNT_MAPPING;
import static razepl.dev.todoapp.api.profile.constants.ProfileMappings.GET_USER_DATA_MAPPING;
import static razepl.dev.todoapp.api.profile.constants.ProfileMappings.PROFILE_MAPPING;
import static razepl.dev.todoapp.api.profile.constants.ProfileMappings.REMOVE_SOCIAL_ACCOUNT_MAPPING;


@RestController
@RequestMapping(value = PROFILE_MAPPING)
@RequiredArgsConstructor
public class ProfileControllerImpl implements ProfileController {
    private final ProfileService profileService;

    @Override
    @GetMapping(value = GET_USER_DATA_MAPPING)
    public final UserResponse getUserData(@AuthenticationPrincipal User user) {
        return profileService.getUserData(user);
    }

    @Override
    @DeleteMapping(value = CLOSE_ACCOUNT_MAPPING)
    public final UserResponse closeUsersAccount(@AuthenticationPrincipal User user) {
        return profileService.closeUsersAccount(user);
    }

    @Override
    @PostMapping(value = ADD_SOCIAL_ACCOUNT_MAPPING)
    public final UserResponse addNewSocialAccount(@RequestBody SocialAccountRequest socialAccountRequest,
                                                  @AuthenticationPrincipal User user) {
        return profileService.addNewSocialAccount(socialAccountRequest, user);
    }

    @Override
    @DeleteMapping(value = REMOVE_SOCIAL_ACCOUNT_MAPPING)
    public final UserResponse removeSocialAccount(@RequestParam long socialAccountId,
                                                  @AuthenticationPrincipal User user) {
        return profileService.removeSocialAccount(socialAccountId, user);
    }
}
