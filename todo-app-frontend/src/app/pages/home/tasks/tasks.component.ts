import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { GroupService } from "@core/services/home/group.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { AuthService } from "@core/services/auth/auth.service";
import { Observable, Subject, take, takeUntil } from "rxjs";
import { Group } from "@core/data/home/Group";
import { Task } from "@core/data/home/Task";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { TaskRequest } from "@core/data/home/TaskRequest";
import { TaskUpdate } from "@core/data/home/TaskUpdate";
import { TaskService } from "@core/services/home/task.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { data } from "autoprefixer";

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    private numOfPage: number = 0;
    protected notCompletedTasks!: Task[];
    protected completedTasks !: Task[];
    protected isEditingGroupName: boolean = false;
    protected group !: Group;
    protected readonly editGroupControl: FormControl = new FormControl("", [
        Validators.required
    ]);
    protected readonly newTaskControl: FormControl<string | null> = new FormControl("", [
        Validators.required
    ]);

    constructor(private groupService: GroupService,
                private authService: AuthService,
                private formBuilder: FormBuilder,
                private utilService: UtilService,
                private taskService: TaskService,
                private sideMenuService: SideMenuService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    ngOnInit(): void {
        this.group = this.groupService.group;

        this.getTaskList(false)
            .pipe(take(1))
            .subscribe(data => {
                this.notCompletedTasks = data;
            });
        this.getTaskList(true)
            .pipe(take(1))
            .subscribe(data => {
                this.completedTasks = data;
            });
    }

    completeEvent(event: Task): void {
        this.taskService.updateCompleteStatus(event.taskId)
            .pipe(take(1))
            .subscribe(data => {
                this.notCompletedTasks = this.notCompletedTasks.filter(task => task !== event);

                this.completedTasks.push(data);
            });
    }

    unCompleteEvent(event: Task): void {
        this.taskService.updateCompleteStatus(event.taskId)
            .pipe(take(1))
            .subscribe(data => {
                this.completedTasks = this.completedTasks.filter(task => task !== event);

                this.notCompletedTasks.push(data);
            });
    }

    private getTaskList(isCompleted: boolean): Observable<Task[]> {
        return this.taskService.getListOfTasks({
            pageNumber: this.numOfPage,
            isCompleted: isCompleted,
            groupId: this.group.groupId
        });
    }

    removeCurrentGroup(): void {
        this.groupService
            .removeGroup(this.group)
            .pipe(take(1))
            .subscribe(data => {
                this.utilService.navigate(RouterPath.GROUPS_DIRECT);
            });
    }

    editGroupName(): void {
        this.isEditingGroupName = !this.isEditingGroupName;
    }

    submitNewGroupName(): void {
        if (this.editGroupControl.invalid) {
            return;
        }
        this.groupService.editGroupsName(this.group.groupName, this.editGroupControl.value!)
            .pipe(take(1))
            .subscribe(data => {
                this.group = data;
                this.groupService.group = data;
                this.isEditingGroupName = !this.isEditingGroupName;
            });
    }

    addNewTask(): void {
        const newTask: TaskRequest = {
            title: this.newTaskControl.value!,
            description: "",
            priority: 0,
            dueDate: "",
            groupName: this.group.groupName
        };

        this.taskService.addNewTask(newTask)
            .pipe(take(1))
            .subscribe(data => this.notCompletedTasks.push(data));
    }

    editNotCompletedTask(event: TaskUpdate): void {
        this.taskService.editNotCompletedTask(event)
            .pipe(take(1))
            .subscribe(data => {
                this.notCompletedTasks = this.notCompletedTasks.filter(task => task.taskId !== data.taskId);

                this.notCompletedTasks.push(data);
            });
    }

    deleteTaskFromNotCompleted(event: Task): void {
        this.taskService.deleteTask(event.taskId)
            .pipe(take(1))
            .subscribe(() => {
                this.notCompletedTasks = this.notCompletedTasks.filter(task => task !== event);
            });
    }

    deleteTaskFromCompleted(event: Task): void {
        this.taskService.deleteTask(event.taskId)
            .pipe(take(1))
            .subscribe(data => {
                this.completedTasks = this.completedTasks.filter(task => task !== event);
            });
    }

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }

    protected readonly RouterPath = RouterPath;
}
