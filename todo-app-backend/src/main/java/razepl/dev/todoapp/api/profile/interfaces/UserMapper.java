package razepl.dev.todoapp.api.profile.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.profile.data.UserResponse;
import razepl.dev.todoapp.entities.user.User;

@FunctionalInterface
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
