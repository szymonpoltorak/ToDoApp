import { Component, OnInit } from '@angular/core';
import { RouterPath } from "@enums/RouterPath";
import { Observable } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { Session } from "@core/interfaces/home/Session";
import { SessionService } from "@core/services/home/session.service";
import { MatPaginatorModule, PageEvent } from "@angular/material/paginator";
import { MatCardModule } from "@angular/material/card";
import { MatDividerModule } from "@angular/material/divider";
import { SessionComponent } from "./session/session.component";
import { AsyncPipe, CommonModule } from "@angular/common";

@Component({
    selector: 'app-sessions',
    templateUrl: './sessions.component.html',
    standalone: true,
    imports: [
        MatCardModule,
        MatDividerModule,
        SessionComponent,
        AsyncPipe,
        MatPaginatorModule,
        CommonModule
    ],
    styleUrls: ['./sessions.component.scss']
})
export class SessionsComponent implements OnInit {
    protected readonly RouterPath = RouterPath;
    sessions$ !: Observable<Session[]>;

    constructor(private authService: AuthService,
                private sessionService: SessionService) {
    }

    ngOnInit(): void {
        this.sessions$ = this.sessionService.getSessionsOnPage(0);
    }

    changePage(event: PageEvent): void {
        this.sessions$ = this.sessionService.getSessionsOnPage(event.pageIndex);
    }
}
