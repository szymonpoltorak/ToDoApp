import { Injectable } from '@angular/core';
import { Router } from "@angular/router";
import { LocalStorageService } from "./local-storage.service";
import { HttpClient } from "@angular/common/http";
import { Observable } from "rxjs";
import { environment } from "@environments/environment";
import { StorageKeys } from "@enums/auth/StorageKeys";
import { AuthResponse } from "@core/data/auth/auth-response";

@Injectable({
    providedIn: 'root'
})
export class UtilService {
    constructor(private router: Router,
                private localStorageService: LocalStorageService,
                private http: HttpClient) {
    }

    navigate(url: string): void {
        this.router.navigateByUrl(url);
    }

    clearStorage(): void {
        this.localStorageService.clearStorage();
    }

    addValueToStorage(key: StorageKeys, value: string): void {
        this.localStorageService.addValueIntoStorage(key, value);
    }

    getValueFromStorage(key: StorageKeys): string {
        return this.localStorageService.getValueFromStorage(key);
    }

    getKeyValuePairFromStorage(key: StorageKeys): string {
        return this.localStorageService.getKeyValueFromStorage(key);
    }

    removeValueFromStorage(key: StorageKeys): void {
        this.localStorageService.removeValueFromStorage(key);
    }

    buildTestData(): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${environment.httpBackend}/api/test`, {});
    }
}
