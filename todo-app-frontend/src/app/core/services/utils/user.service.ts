import { Injectable } from '@angular/core';
import { UtilService } from "@core/services/utils/util.service";
import { StorageKeys } from "@enums/auth/StorageKeys";

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private isAuthenticated !: boolean;

    constructor(private utilService: UtilService) {
    }

    get isUserAuthenticated(): boolean {
        const authToken: string = this.utilService.getValueFromStorage(StorageKeys.AUTH_TOKEN);

        this.isAuthenticated = authToken !== "undefined" && authToken !== "";

        return this.isAuthenticated;
    }

    set setUserAuthentication(isAuthenticated: boolean) {
        this.isAuthenticated = isAuthenticated;
    }
}
