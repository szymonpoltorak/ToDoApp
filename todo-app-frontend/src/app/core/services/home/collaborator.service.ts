import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Collaborator } from "@core/data/home/Collaborator";
import { environment } from "@environments/environment";

@Injectable({
    providedIn: 'root'
})
export class CollaboratorService {
    constructor(private httpClient: HttpClient) {
    }

    getListOfCollaborators(): Observable<Collaborator[]> {
        return this.httpClient.get<Collaborator[]>(`${environment.httpBackend}/api/collaborator/listOfCollaborators`);
    }

    removeCollaborator(collaboratorId: number): Observable<Collaborator> {
        return this.httpClient.delete<Collaborator>(`${environment.httpBackend}/api/collaborator/deleteCollaborator`, {
            params: {
                collaboratorId: collaboratorId
            }
        });
    }
}
