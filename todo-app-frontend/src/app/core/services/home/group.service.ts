import { Injectable } from '@angular/core';
import { Group } from "@core/data/home/Group";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "@environments/environment";

@Injectable({
    providedIn: 'root'
})
export class GroupService {
    private _group !: Group;

    constructor(private httpClient: HttpClient) {
    }

    getListOfGroups(numOfPage: number): Observable<Group[]> {
        return this.httpClient.get<Group[]>(`${environment.httpBackend}/api/groups/getGroups`, {
            params: {
                numOfPage: numOfPage
            }
        });
    }

    get group() {
        return this._group;
    }

    set group(group: Group) {
        this._group = group;
    }

    createNewGroup(): Observable<Group> {
        return this.httpClient.post<Group>(`${environment.httpBackend}/api/groups/addNewGroup`, {}, {
            params: {
                groupName: "New Group"
            }
        });
    }

    editGroupsName(oldGroupName: string, newGroupName: string): Observable<Group> {
        return this.httpClient.patch<Group>(`${environment.httpBackend}/api/groups/editGroupName`, {}, {
            params: {
                oldGroupName: oldGroupName,
                newGroupName: newGroupName
            }
        });
    }

    removeGroup(group: Group): Observable<Group>{
        return this.httpClient.delete<Group>(`${environment.httpBackend}/api/groups/deleteGroup`, {
            params: {
                groupId: group.groupId
            }
        });
    }
}
