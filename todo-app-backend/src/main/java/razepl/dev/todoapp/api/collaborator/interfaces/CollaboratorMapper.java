package razepl.dev.todoapp.api.collaborator.interfaces;

import org.mapstruct.Mapper;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.entities.collaborator.Collaborator;

@Mapper(componentModel = "spring")
public interface CollaboratorMapper {
    CollaboratorResponse toCollaboratorResponse(Collaborator collaborator);
}
