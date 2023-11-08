package razepl.dev.todoapp.api.collaborator.constants;

public final class CollaboratorMappings {
    public static final String COLLABORATOR_ENDPOINT_MAPPING = "/api/collaborator";

    public static final String GET_LIST_OF_COLLABORATORS_MAPPING = "listOfCollaborators";

    public static final String GET_COLLABORATORS_OF_TASK = "collaboratorsOfTask";

    public static final String ADD_USER_AS_COLLABORATOR = "addCollaborator";

    public static final String ASSIGN_COLLABORATOR_TO_TASK = "assignToTask";

    public static final String FIND_COLLABORATORS_BY_PATTERN = "find";

    public static final String REMOVE_USER_FROM_COLLABORATORS = "deleteCollaborator";

    private CollaboratorMappings() {
    }
}
