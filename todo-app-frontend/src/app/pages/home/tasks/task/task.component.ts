import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Task } from "@core/data/home/Task";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { TaskRequest } from "@core/data/home/TaskRequest";
import { Group } from "@core/data/home/Group";
import { TaskUpdate } from "@core/data/home/TaskUpdate";


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
    protected descriptionControl !: FormControl;
    protected taskGroup !: FormGroup;
    protected taskNameControl !: FormControl;
    protected dateControl !: FormControl<Date | null>;
    protected priorities : number[] = Array.from({ length: 11 }, (_, i) => i);

    constructor(private formBuilder: FormBuilder) {
    }

    ngOnInit(): void {
        this.descriptionControl = new FormControl(this.task.description, []);
        this.taskNameControl = new FormControl(this.task.title, [
            Validators.required
        ]);
        this.dateControl = new FormControl<Date>(new Date(this.task.dueDate), []);

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
            priority: this.task.priority
        }
        this.updateTask.emit(taskUpdate);
    }

    changeCompletionStatus(): void {
        this.completeEvent.emit(this.task);
    }

    deleteTask(): void {
        console.log("Delete task !");
    }
}
