import { Injectable } from '@angular/core';
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";

@Injectable({
    providedIn: 'root'
})
export class SideMenuService implements SideMenuActions {
    constructor(private utilService: UtilService) {
    }

    changeToProfileView(): void {
        this.utilService.navigate(RouterPaths.PROFILE_DIRECT);
    }

    logoutUser(): void {
        this.utilService.clearStorage();

        this.utilService.navigate(RouterPaths.LOGIN_DIRECT);
    }

    changeToGroupsView(): void {
        this.utilService.navigate(RouterPaths.GROUPS_DIRECT);
    }

    changeToCollaboratorsView(): void {
        this.utilService.navigate(RouterPaths.COLLABORATORS_DIRECT);
    }

    changeToSearchView(): void {
        this.utilService.navigate(RouterPaths.SEARCH_DIRECT);
    }
}
