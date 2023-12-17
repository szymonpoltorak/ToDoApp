import { Component, Input } from '@angular/core';
import { Group } from "@core/data/home/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";

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
        this.groupService.group = this.group;

        this.utilService.navigate(RouterPath.TASKS_DIRECT);
    }
}
