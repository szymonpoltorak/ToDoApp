import { Component, OnInit } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";
import { GroupService } from "@core/services/home/group.service";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements SideNavActions, OnInit {
    constructor(private groupService: GroupService) {
    }

    logoutUser(): void {
    }

    changeToGroupsView(): void {
    }

    changeToProfileView(): void {
    }

    ngOnInit(): void {
        console.log(this.groupService.group);
    }
}
