package razepl.dev.todoapp.api.collaborator.interfaces;

import razepl.dev.todoapp.api.collaborator.data.CollaboratorRequest;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorSuggestion;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

public interface CollaboratorController {
    List<CollaboratorResponse> getListOfCollaborators(User user);

    List<CollaboratorResponse> getCollaboratorsAssignedToTask(long taskId, User user);

    CollaboratorResponse addUserAsCollaborator(String collaboratorUsername, User user);

    CollaboratorResponse assignCollaboratorToTask(CollaboratorRequest collaboratorRequest);

    List<CollaboratorSuggestion> findCollaboratorsByPattern(String searchPattern);

    CollaboratorResponse removeUserFromCollaborators(long collaboratorId);
}
