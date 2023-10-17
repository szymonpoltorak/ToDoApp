import { Injectable } from '@angular/core';
import { UtilService } from "@core/services/utils/util.service";
import { StorageKeys } from "@enums/auth/StorageKeys";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private isAuthenticated !: boolean;
    private wasLoggedOut !: boolean;

    constructor(private utilService: UtilService) {
    }

    get isUserAuthenticated(): boolean {
        if (!this.isAuthenticated) {
            this.isAuthenticated = !!this.utilService.getValueFromStorage(StorageKeys.AUTH_TOKEN);

            return this.isAuthenticated;
        }
        return true;
    }

    get wasUserLoggedOut(): boolean {
        return this.wasLoggedOut;
    }

    set setWasUserLoggedOut(wasLoggedOut: boolean) {
        this.wasLoggedOut = wasLoggedOut;
    }

    set setUserAuthentication(isAuthenticated: boolean) {
        this.isAuthenticated = isAuthenticated;
    }
}
