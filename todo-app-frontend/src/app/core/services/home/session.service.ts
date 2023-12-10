import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { Session } from "@core/interfaces/home/Session";
import { environment } from "@environments/environment";

@Injectable({
    providedIn: 'root'
})
export class SessionService {
    constructor(private http: HttpClient) {
    }

    getSessionsOnPage(page: number): Observable<Session[]> {
        return this.http.get<Session[]>(`${environment.httpBackend}/api/auth/devices/getLoggedDevices`, {
            params: {
                page: page
            }
        });
    }
}
