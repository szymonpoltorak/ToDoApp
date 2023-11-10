import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Task } from "@core/data/home/Task";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { Group } from "@core/data/home/Group";
import { TaskUpdate } from "@core/data/home/TaskUpdate";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorService } from "@core/services/home/collaborator.service";
import { Observable } from "rxjs";


@Component({
    selector: 'app-task',
    templateUrl: './task.component.html',
    styleUrls: ['./task.component.scss']
})
export class TaskComponent implements OnInit {
    @Input() task !: Task;
    @Input() group !: Group;
    @Output() readonly completeEvent: EventEmitter<Task> = new EventEmitter<Task>();
    @Output() readonly updateTask: EventEmitter<TaskUpdate> = new EventEmitter<TaskUpdate>();
    @Output() readonly deleteTaskEvent: EventEmitter<Task> = new EventEmitter<Task>();
    protected descriptionControl !: FormControl;
    protected taskGroup !: FormGroup;
    protected taskNameControl !: FormControl;
    protected dateControl !: FormControl<Date | null>;
    protected priorities : number[] = Array.from({ length: 11 }, (_, i) => i);
    collaboratorsControl !: FormControl<string[] | null>;
    collaborators$ !: Observable<Collaborator[]>;

    constructor(private formBuilder: FormBuilder,
                private collaboratorService: CollaboratorService) {
    }

    ngOnInit(): void {
        this.descriptionControl = new FormControl(this.task.description, []);
        this.taskNameControl = new FormControl(this.task.title, [
            Validators.required
        ]);
        this.dateControl = new FormControl<Date>(new Date(this.task.dueDate), []);
        this.collaboratorsControl = new FormControl<string[]>(this.task.collaborators.map(c => c.username));
        this.collaborators$ = this.collaboratorService.getListOfCollaborators();

        this.taskGroup = this.formBuilder.group({
            taskName: this.taskNameControl,
            description: this.descriptionControl,
            date: this.dateControl
        });
    }

    submitTask(): void {
        const taskUpdate: TaskUpdate = {
            taskId: this.task.taskId,
            title: this.taskNameControl.value,
            description: this.descriptionControl.value,
            dueDate: this.dateControl.value!.toLocaleDateString().replaceAll("/", "-"),
            priority: this.task.priority,
            collaboratorUsernames: this.collaboratorsControl.value!
        }
        this.updateTask.emit(taskUpdate);
    }

    changeCompletionStatus(): void {
        this.completeEvent.emit(this.task);
    }

    deleteTask(): void {
        this.deleteTaskEvent.emit(this.task);
    }
}
