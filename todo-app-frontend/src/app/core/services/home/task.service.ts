import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { TaskListRequest } from "@core/data/home/TaskListRequest";
import { Task } from "@core/data/home/Task";
import { environment } from "@environments/environment";

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
}
