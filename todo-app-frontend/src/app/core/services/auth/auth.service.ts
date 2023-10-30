import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { catchError, Observable, of, tap } from "rxjs";
import { environment } from "@environments/environment";
import { UtilService } from "@core/services/utils/util.service";
import { StorageKeys } from "@enums/auth/StorageKeys";
import { AuthApiCalls } from "@enums/auth/AuthApiCalls";
import { RegisterRequest } from "@core/data/auth/register-request";
import { AuthResponse } from "@core/data/auth/auth-response";
import { LoginRequest } from "@core/data/auth/login-request";
import { AuthConstants } from "@enums/auth/AuthConstants";

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient,
                private utilService: UtilService) {
    }

    logoutUser(): Observable<any> {
        return this.http.post(`${environment.httpBackend}${AuthApiCalls.LOGOUT_URL}`, {});
    }

    registerUser(registerRequest: RegisterRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${environment.httpBackend}${AuthApiCalls.REGISTER_URL}`,
            registerRequest)
            .pipe(catchError(() => of(JSON.parse(AuthApiCalls.ERROR_FOUND))));
    }

    loginUser(loginRequest: LoginRequest): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${environment.httpBackend}${AuthApiCalls.LOGIN_URL}`, loginRequest)
            .pipe(catchError(() => of(JSON.parse(AuthApiCalls.ERROR_FOUND))));
    }

    refreshUsersToken(refreshToken: string): Observable<AuthResponse> {
        return this.http.post<AuthResponse>(`${environment.httpBackend}${AuthApiCalls.REFRESH_URL}`,
            this.buildRefreshToken(refreshToken)).pipe(tap((response: AuthResponse) => {
            this.saveData(response);
        }));
    }

    saveData(data: AuthResponse): void {
        console.log(data);

        if (data.authToken === AuthConstants.NO_TOKEN || data.refreshToken === AuthConstants.NO_TOKEN) {
            return;
        }
        this.utilService.addValueToStorage(StorageKeys.AUTH_TOKEN, data.authToken);
        this.utilService.addValueToStorage(StorageKeys.REFRESH_TOKEN, data.refreshToken);
    }

    private buildAuthRequest(authToken: string) {
        console.log(JSON.parse(`{${authToken}}`));

        return JSON.parse(`{${authToken}}`);
    }

    private buildRefreshToken(refreshToken: string) {
        return JSON.parse(`{"refreshToken": "${refreshToken}"}`);
    }
}
