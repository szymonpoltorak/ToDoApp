package razepl.dev.todoapp.api.collaborator.data;

import lombok.Builder;

@Builder
public record CollaboratorResponse(String fullName, String username, long collaboratorId) {
}
