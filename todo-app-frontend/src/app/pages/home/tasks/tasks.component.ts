import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { GroupService } from "@core/services/home/group.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { AuthService } from "@core/services/auth/auth.service";
import { combineLatest, map, mergeMap, Observable, of, Subject, takeUntil } from "rxjs";
import { Group } from "@core/data/home/Group";
import { Task } from "@core/data/home/Task";
import { FormBuilder, FormControl, Validators } from "@angular/forms";
import { TaskRequest } from "@core/data/home/TaskRequest";
import { TaskUpdate } from "@core/data/home/TaskUpdate";

@Component({
    selector: 'app-tasks',
    templateUrl: './tasks.component.html',
    styleUrls: ['./tasks.component.scss']
})
export class TasksComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    protected notCompletedTasks$ !: Observable<Task[]>;
    protected completedTasks$ !: Observable<Task[]>;
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

        const notCompleted: Task[] = [];
        const completed: Task[] = [];

        for (let i = 0; i < 5; i++) {
            notCompleted.push(
                {
                    taskId: i,
                    title: `Task ${i}`,
                    description: `Long task description that you really need to see!`,
                    isCompleted: false,
                    priority: 0,
                    dueDate: "11-11-2023"
                }
            );
            completed.push(
                {
                    taskId: i + 5,
                    title: `Task ${i + 5}`,
                    description: `Long task description that you really need to see!`,
                    isCompleted: true,
                    priority: 0,
                    dueDate: "11-11-2023"
                }
            );
        }
        this.notCompletedTasks$ = of(notCompleted);
        this.completedTasks$ = of(completed);

        console.log(this.group);
    }

    changeToCollaboratorsView(): void {
        this.sideMenuService.changeToCollaboratorsView();
    }

    changeToSearchView(): void {
        this.sideMenuService.changeToSearchView();
    }

    completeEvent(event: Task): void {
        if (event.isCompleted) {
            this.completedTasks$ = this.completedTasks$.pipe(
                map((tasks: Task[]) => tasks.filter(task => task !== event))
            );
            event.isCompleted = !event.isCompleted;

            this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
                map(tasks => [...tasks, event])
            );
        } else {
            this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
                map((tasks: Task[]) => tasks.filter(task => task !== event))
            );
            event.isCompleted = !event.isCompleted;

            this.completedTasks$ = this.completedTasks$.pipe(
                map(tasks => [...tasks, event])
            );
        }
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

        this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
            map(tasks => [...tasks, task])
        );
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

        this.notCompletedTasks$ = this.notCompletedTasks$.pipe(
            mergeMap(tasks => {
                return of(task).pipe(
                    map(task => tasks.concat([task]))
                );
            })
        );
    }
}
