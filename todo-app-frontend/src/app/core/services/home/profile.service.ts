import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "@environments/environment";
import { User } from "@core/data/home/User";

@Injectable({
    providedIn: 'root'
})
export class ProfileService {
    constructor(private httpClient: HttpClient) {
    }

    getUserData(): Observable<User> {
        return this.httpClient.get<User>(`${environment.httpBackend}/api/home/profile/userData`, {});
    }

    closeAccount(): Observable<User> {
        return this.httpClient.delete<User>(`${environment.httpBackend}/api/home/profile/closeAccount`);
    }
}
