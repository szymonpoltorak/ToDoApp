import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TaskListRequest } from "@core/data/home/TaskListRequest";
import { Task } from "@core/data/home/Task";
import { environment } from "@environments/environment";
import { TaskRequest } from "@core/data/home/TaskRequest";
import { TaskUpdate } from "@core/data/home/TaskUpdate";

@Injectable({
    providedIn: 'root'
})
export class TaskService {
    constructor(private httpClient: HttpClient) {
    }

    getListOfTasks(request: TaskListRequest): Observable<Task[]> {
        return this.httpClient.get<Task[]>(`${environment.httpBackend}/api/tasks/getTasks`, {
            params: {
                pageNumber: request.pageNumber,
                isCompleted: request.isCompleted,
                groupId: request.groupId
            }
        });
    }

    updateCompleteStatus(taskId: number): Observable<Task> {
        return this.httpClient.patch<Task>(`${environment.httpBackend}/api/tasks/completeTask`, {}, {
            params: {
                taskId: taskId
            }
        });
    }

    addNewTask(newTask: TaskRequest): Observable<Task> {
        return this.httpClient.post<Task>(`${environment.httpBackend}/api/tasks/createTask`, newTask);
    }

    editNotCompletedTask(taskUpdate: TaskUpdate): Observable<Task> {
        return this.httpClient.patch<Task>(`${environment.httpBackend}/api/tasks/updateTask`, taskUpdate);
    }

    deleteTask(taskId: number): Observable<Task> {
        return this.httpClient.delete<Task>(`${environment.httpBackend}/api/tasks/deleteTask`, {
            params: {
                taskId: taskId
            }
        });
    }
}
