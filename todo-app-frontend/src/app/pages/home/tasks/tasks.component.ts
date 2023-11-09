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
                private taskService: TaskService,
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

        this.getTaskList(false)
            .pipe(take(1))
            .subscribe(data => {
                console.log(data);
                
                this.notCompletedTasks = data;
            });
        this.getTaskList(true)
            .pipe(take(1))
            .subscribe(data => {
                console.log(data);

                this.completedTasks = data;
            });
    }

    changeToCollaboratorsView(): void {
        this.sideMenuService.changeToCollaboratorsView();
    }

    changeToSearchView(): void {
        this.sideMenuService.changeToSearchView();
    }

    completeEvent(event: Task): void {
        this.updateTaskList(event)
            .pipe(take(1))
            .subscribe(data => {
                console.log(`Event : ${JSON.stringify(event)}`)
                console.log(`Data : ${JSON.stringify(data)}`);

                this.notCompletedTasks = this.notCompletedTasks.filter(task => task !== event);

                this.completedTasks.push(data);
            });
    }

    unCompleteEvent(event: Task): void {
        this.updateTaskList(event)
            .pipe(take(1))
            .subscribe(data => {
                console.log(`Event : ${JSON.stringify(event)}`)
                console.log(`Data : ${JSON.stringify(data)}`);

                this.completedTasks = this.completedTasks.filter(task => task !== event);

                this.notCompletedTasks.push(data);
            });
    }

    private updateTaskList(oldTask: Task): Observable<Task> {
        return this.taskService.updateCompleteStatus(oldTask.taskId);
    }

    private getTaskList(isCompleted: boolean): Observable<Task[]> {
        return this.taskService.getListOfTasks({
            pageNumber: this.numOfPage,
            isCompleted: isCompleted,
            groupId: this.group.groupId
        });
    }

    removeCurrentGroup(): void {
        console.log("Removing group!");
    }

    editGroupName(): void {
        this.isEditingGroupName = !this.isEditingGroupName;
    }

    submitNewGroupName(): void {
        if (this.editGroupControl.invalid) {
            return;
        }
        this.group.groupName = this.editGroupControl.value;
        this.groupService.group = this.group;
        this.isEditingGroupName = !this.isEditingGroupName;
    }

    addNewTask(): void {
        const newTask: TaskRequest = {
            title: this.newTaskControl.value!,
            description: "",
            priority: 0,
            dueDate: "",
            groupName: this.group.groupName
        };
        console.log(newTask);

        // TODO REMOVE IT
        const task: Task = {
            title: newTask.title,
            description: newTask.description,
            priority: newTask.priority,
            dueDate: newTask.dueDate,
            taskId: 10,
            isCompleted: false
        };

        // this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
        //     map(tasks => [...tasks, task])
        // );
    }

    editNotCompletedTask(event: TaskUpdate): void {
        const task: Task = {
            taskId: 5,
            title: `Task ${99}`,
            description: `Long task description that you really need to see!`,
            isCompleted: false,
            priority: 0,
            dueDate: "22-22-2025"
        };
        console.log("Entering!");

        // this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
        //     mergeMap(tasks => {
        //         return of(task).pipe(
        //             map(task => tasks.concat([task]))
        //         );
        //     })
        // );
    }
}
