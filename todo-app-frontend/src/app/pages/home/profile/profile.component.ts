import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { Observable, of, Subject, takeUntil } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { User } from "@core/data/home/User";
import { ProfileService } from "@core/services/home/profile.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements SideMenuActions, OnInit {
    user$: Observable<User> = of({ name: "Jan", surname: "Kowalski", username: "jan@example.com" });
    private destroyLogout$: Subject<void> = new Subject<void>();
    private destroyClosingAccount$: Subject<void> = new Subject<void>();

    constructor(private sideMenuService: SideMenuService,
                private profileService: ProfileService,
                private utilService: UtilService,
                private authService: AuthService) {
    }

    changeToGroupsView(): void {
        this.sideMenuService.changeToGroupsView();
    }

    changeToProfileView(): void {
        this.sideMenuService.changeToProfileView();
    }

    changeToCollaboratorsView(): void {
        this.sideMenuService.changeToCollaboratorsView();
    }

    changeToSearchView(): void {
        this.sideMenuService.changeToSearchView();
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    closeAccount(): void {
        this.profileService.closeAccount()
            .pipe(takeUntil(this.destroyClosingAccount$))
            .subscribe((): void => {
                this.utilService.clearStorage();

                this.utilService.navigate(RouterPaths.LOGIN_DIRECT);
            });
    }

    ngOnInit(): void {
        this.user$ = this.profileService.getUserData();
    }
}
