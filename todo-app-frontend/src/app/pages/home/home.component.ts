import { Component } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { RouterPath } from "@enums/RouterPath";
import { take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.scss']
})
export class HomeComponent implements SideMenuActions {
    protected readonly RouterPath = RouterPath;

    constructor(private authService: AuthService,
                private sideMenuService: SideMenuService) {
    }

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(take(1))
            .subscribe(() => this.sideMenuService.logoutUser());
    }
}
