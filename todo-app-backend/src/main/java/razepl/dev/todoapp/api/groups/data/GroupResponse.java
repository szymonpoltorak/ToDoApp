package razepl.dev.todoapp.api.groups.data;

import lombok.Builder;

@Builder
public record GroupResponse(long groupId, String groupName) {
}
