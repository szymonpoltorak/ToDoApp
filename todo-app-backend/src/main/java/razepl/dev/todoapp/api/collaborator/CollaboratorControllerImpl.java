package razepl.dev.todoapp.api.collaborator;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorRequest;
import razepl.dev.todoapp.api.collaborator.data.CollaboratorResponse;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorController;
import razepl.dev.todoapp.api.collaborator.interfaces.CollaboratorService;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

import static razepl.dev.todoapp.api.collaborator.constants.CollaboratorMappings.ADD_USER_AS_COLLABORATOR;
import static razepl.dev.todoapp.api.collaborator.constants.CollaboratorMappings.ASSIGN_COLLABORATOR_TO_TASK;
import static razepl.dev.todoapp.api.collaborator.constants.CollaboratorMappings.COLLABORATOR_ENDPOINT_MAPPING;
import static razepl.dev.todoapp.api.collaborator.constants.CollaboratorMappings.GET_COLLABORATORS_OF_TASK;
import static razepl.dev.todoapp.api.collaborator.constants.CollaboratorMappings.GET_LIST_OF_COLLABORATORS_MAPPING;

@RestController
@RequestMapping(value = COLLABORATOR_ENDPOINT_MAPPING)
@RequiredArgsConstructor
public class CollaboratorControllerImpl implements CollaboratorController {
    private final CollaboratorService collaboratorService;

    @Override
    @GetMapping(value = GET_LIST_OF_COLLABORATORS_MAPPING)
    public final List<CollaboratorResponse> getListOfCollaborators(@AuthenticationPrincipal User user) {
        return collaboratorService.getListOfCollaborators(user);
    }

    @Override
    @GetMapping(value = GET_COLLABORATORS_OF_TASK)
    public final List<CollaboratorResponse> getCollaboratorsAssignedToTask(@RequestParam long taskId,
                                                                           @AuthenticationPrincipal User user) {
        return collaboratorService.getCollaboratorsAssignedToTask(taskId, user);
    }

    @Override
    @PostMapping(value = ADD_USER_AS_COLLABORATOR)
    public final CollaboratorResponse addUserAsCollaborator(@RequestParam String collaboratorUsername,
                                                            @AuthenticationPrincipal User user) {
        return collaboratorService.addUserAsCollaborator(collaboratorUsername, user);
    }

    @Override
    @PatchMapping(value = ASSIGN_COLLABORATOR_TO_TASK)
    public final CollaboratorResponse assignCollaboratorToTask(@RequestBody CollaboratorRequest collaboratorRequest) {
        return collaboratorService.assignCollaboratorToTask(collaboratorRequest);
    }
}
