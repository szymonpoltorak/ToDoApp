import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { Observable, of, take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { User } from "@core/data/home/User";
import { ProfileService } from "@core/services/home/profile.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements SideMenuActions, OnInit {
    user$: Observable<User> = of({ name: "Jan", surname: "Kowalski", username: "jan@example.com" });

    constructor(private sideMenuService: SideMenuService,
                private profileService: ProfileService,
                private utilService: UtilService,
                private authService: AuthService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(take(1))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    closeAccount(): void {
        this.profileService.closeAccount()
            .pipe(take(1))
            .subscribe((): void => {
                this.utilService.clearStorage();

                this.utilService.navigate(RouterPath.LOGIN_DIRECT);
            });
    }

    ngOnInit(): void {
        this.user$ = this.profileService.getUserData();
    }

    protected readonly RouterPath = RouterPath;

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }
}
