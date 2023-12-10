import { Component, OnInit } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { Observable, of, take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { Session } from "@core/interfaces/home/Session";
import { DeviceType } from "@enums/home/DeviceType";

@Component({
  selector: 'app-sessions',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.scss']
})
export class SessionsComponent implements SideMenuActions, OnInit {
    protected readonly RouterPath = RouterPath;
    sessions$ !: Observable<Session[]>;

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

    ngOnInit(): void {
        const sessions = [
            {
                dateOfLogin: '2021-08-18',
                ipAddress: "192.168.0.1",
                deviceType: DeviceType.DESKTOP,
                timeOfLogin: "10:00",
            },
            {
                dateOfLogin: '2021-08-18',
                ipAddress: "192.168.12.12",
                deviceType: DeviceType.MOBILE,
                timeOfLogin: "12:00",
            }
        ]
        this.sessions$ = of(sessions);
    }

    changePage(event: any): void {

    }
}
