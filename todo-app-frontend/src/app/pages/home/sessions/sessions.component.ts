import { Component } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";

@Component({
  selector: 'app-sessions',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.scss']
})
export class SessionsComponent implements SideMenuActions {
    protected readonly RouterPath = RouterPath;

    constructor(private sideMenuService: SideMenuService,
                private authService: AuthService) {
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
