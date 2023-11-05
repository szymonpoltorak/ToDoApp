import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { GroupService } from "@core/services/home/group.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { AuthService } from "@core/services/auth/auth.service";
import { Observable, of, Subject, takeUntil } from "rxjs";
import { Group } from "@core/data/home/Group";
import { Task } from "@core/data/home/Task";

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    protected notCompletedTasks$ !: Observable<Task[]>;
    protected group !: Group;

    constructor(private groupService: GroupService,
                private authService: AuthService,
                private sideMenuService: SideMenuService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    changeToGroupsView(): void {
        this.sideMenuService.changeToGroupsView();
    }

    changeToProfileView(): void {
        this.sideMenuService.changeToProfileView();
    }

    ngOnInit(): void {
        this.groupService.group = { groupName: "GroupName", groupId: 1 };

        this.group = this.groupService.group;

        const tasks: Task[] = [];

        for (let i = 0; i < 5; i++) {
            tasks.push(
                {
                    taskId: i,
                    title: `Task ${i}`,
                    description: `Long task description that you really need to see!`,
                    isCompleted: false,
                    priority: 0,
                    dueDate: "11-11-2023"
                }
            );
        }
        this.notCompletedTasks$ = of(tasks);

        console.log(this.group);
    }

    changeToCollaboratorsView(): void {
        this.sideMenuService.changeToCollaboratorsView();
    }

    changeToSearchView(): void {
        this.sideMenuService.changeToSearchView();
    }
}
