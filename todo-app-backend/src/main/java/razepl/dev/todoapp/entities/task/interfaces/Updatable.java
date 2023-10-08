package razepl.dev.todoapp.entities.task.interfaces;

@FunctionalInterface
public interface Updatable <T>{
    void update(T updateData);
}
