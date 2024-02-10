package razepl.dev.todoapp.entities.social.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.profile.data.SocialAccountResponse;
import razepl.dev.todoapp.entities.social.SocialAccount;

@FunctionalInterface
@Mapper(componentModel = "spring")
public interface SocialAccountMapper {
    SocialAccountResponse toSocialAccountResponse(SocialAccount socialAccount);
}
