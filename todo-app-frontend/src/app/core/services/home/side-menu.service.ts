import { Injectable } from '@angular/core';
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";

@Injectable({
    providedIn: 'root'
})
export class SideMenuService implements SideMenuActions {
    constructor(private utilService: UtilService) {
    }

    logoutUser(): void {
        this.utilService.clearStorage();

        this.utilService.navigate(RouterPath.LOGIN_DIRECT);
    }

    changeRouteToNewView(route: RouterPath): void {
        this.utilService.navigate(route);
    }
}
