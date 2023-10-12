package razepl.dev.todoapp.api.collaborator.interfaces;

import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface CollaboratorController {
    List<CollaboratorResponse> getListOfCollaborators(User user);

    List<CollaboratorResponse> getCollaboratorsAssignedToTask(long taskId, User user);
}
