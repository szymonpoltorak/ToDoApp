package razepl.dev.todoapp.api.auth.data;

import razepl.dev.todoapp.entities.user.interfaces.Password;

public record ResetPasswordRequest(@Password String newPassword, String resetPasswordToken) {
}
