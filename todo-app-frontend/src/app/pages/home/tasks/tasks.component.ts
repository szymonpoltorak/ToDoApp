import { Component } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements SideNavActions {
    logoutUser(): void {
    }

    changeToGroupsView(): void {
    }

    changeToProfileView(): void {
    }
}
