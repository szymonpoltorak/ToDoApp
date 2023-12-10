import { Component, OnInit } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { Observable, take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { Session } from "@core/interfaces/home/Session";
import { SessionService } from "@core/services/home/session.service";
import { PageEvent } from "@angular/material/paginator";

@Component({
  selector: 'app-sessions',
  templateUrl: './sessions.component.html',
  styleUrls: ['./sessions.component.scss']
})
export class SessionsComponent implements SideMenuActions, OnInit {
    protected readonly RouterPath = RouterPath;
    sessions$ !: Observable<Session[]>;

    constructor(private sideMenuService: SideMenuService,
                private authService: AuthService,
                private sessionService: SessionService) {
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
        this.sessions$ = this.sessionService.getSessionsOnPage(0);
    }

    changePage(event: PageEvent): void {
        this.sessions$ = this.sessionService.getSessionsOnPage(event.pageIndex);
    }
}
