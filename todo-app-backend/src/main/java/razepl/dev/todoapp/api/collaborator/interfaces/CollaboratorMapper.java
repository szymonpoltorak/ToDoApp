package razepl.dev.todoapp.api.collaborator.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorSuggestion;
import razepl.dev.todoapp.entities.collaborator.Collaborator;
import razepl.dev.todoapp.entities.user.User;

@Mapper(componentModel = "spring")
public interface CollaboratorMapper {
    CollaboratorResponse toCollaboratorResponse(Collaborator collaborator);

    CollaboratorSuggestion toCollaboratorSuggestion(User user);
}
