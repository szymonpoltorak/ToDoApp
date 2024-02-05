import { Component, Input } from '@angular/core';
import { Group } from "@core/data/home/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";

@Component({
    selector: 'app-group',
    standalone: true,
    templateUrl: './group.component.html',
    imports: [
        MatIconModule,
        MatButtonModule
    ],
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
