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
import { ResetPassword } from "@core/data/auth/reset-password";

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
        return this.http.post<AuthResponse>(`${environment.httpBackend}${AuthApiCalls.REFRESH_URL}`, {}, {
            params: {
                refreshToken: refreshToken
            }
        }).pipe(tap((response: AuthResponse) => {
            this.saveData(response);
        }));
    }

    saveData(data: AuthResponse): void {
        if (data.authToken === AuthConstants.NO_TOKEN || data.refreshToken === AuthConstants.NO_TOKEN) {
            return;
        }
        this.utilService.addValueToStorage(StorageKeys.AUTH_TOKEN, data.authToken);
        this.utilService.addValueToStorage(StorageKeys.REFRESH_TOKEN, data.refreshToken);
    }

    sendEmailWithLink(username: string): Observable<string> {
        return this.http.post<string>(`${environment.httpBackend}${AuthApiCalls.FORGOT_PASSWORD_URL}`, {}, {
            params: {
                username: username
            }
        }).pipe(catchError((response) => {
            console.log(response);

            return of(response);
        }));
    }

    resetPassword(request: ResetPassword): Observable<string> {
        return this.http.post<string>(`${environment.httpBackend}${AuthApiCalls.RESET_PASSWORD_URL}`, request);
    }
}
