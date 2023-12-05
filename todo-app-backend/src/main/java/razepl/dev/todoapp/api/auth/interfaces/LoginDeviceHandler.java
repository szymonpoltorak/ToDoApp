package razepl.dev.todoapp.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.todoapp.entities.user.User;

@FunctionalInterface
public interface LoginDeviceHandler {
    void addNewDeviceToUserLoggedInDevices(User user, HttpServletRequest request);
}
