import { Component, Input } from '@angular/core';
import { Group } from "@core/data/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent {
    @Input() group !: Group;

    constructor(private groupService: GroupService,
                private utilService: UtilService) {
    }

    navigateToGroupTasks(): void {
        this.groupService.groupId = this.group.groupId;

        this.utilService.navigate(RouterPaths.TASKS_DIRECT);
    }
}
