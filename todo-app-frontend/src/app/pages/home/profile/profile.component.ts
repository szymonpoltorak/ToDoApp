import { Component } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { Observable, of, Subject, takeUntil } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { User } from "@core/data/home/User";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements SideNavActions {
    private destroyLogout$: Subject<void> = new Subject<void>();
    user$: Observable<User> = of({name: "Jan", surname: "Kowalski", username: "jan@example.com"});

    constructor(private sideMenuService: SideMenuService,
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
        console.log("Closing an account!");
    }
}
