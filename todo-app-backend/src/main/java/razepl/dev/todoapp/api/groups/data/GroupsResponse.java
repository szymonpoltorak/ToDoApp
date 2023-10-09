package razepl.dev.todoapp.api.groups.data;

import lombok.Builder;

@Builder
public record GroupsResponse(long groupId, String groupName) {
}
