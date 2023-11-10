import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Collaborator } from "@core/data/home/Collaborator";
import { environment } from "@environments/environment";
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";

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

    getSuggestions(option: string): Observable<CollaboratorSuggestion[]> {
        return this.httpClient.get<CollaboratorSuggestion[]>(`${environment.httpBackend}/api/collaborator/find`, {
            params: {
                searchPattern: option
            }
        });
    }

    addCollaborator(username: string): Observable<Collaborator> {
        return this.httpClient.post<Collaborator>(`${environment.httpBackend}/api/collaborator/addCollaborator`, {}, {
            params: {
                collaboratorUsername: username
            }
        });
    }
}
