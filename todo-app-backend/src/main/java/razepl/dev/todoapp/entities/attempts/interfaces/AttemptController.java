package razepl.dev.todoapp.entities.attempts.interfaces;

public interface AttemptController {
    void incrementAttempts();

    void resetAttempts();

    boolean isAccountNonLocked();
}
