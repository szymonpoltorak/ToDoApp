package razepl.dev.todoapp.api.profile.interfaces;


import razepl.dev.todoapp.api.profile.data.UserResponse;
import razepl.dev.todoapp.entities.user.User;

public interface ProfileService {
    UserResponse getUserData(User user);

    UserResponse closeUsersAccount(User user);
}
