import { Component, OnDestroy, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { PageEvent } from "@angular/material/paginator";
import { Observable, Subject, takeUntil } from "rxjs";
import { Group } from "@core/data/home/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";

@Component({
    selector: 'app-groups',
    templateUrl: './groups.component.html',
    styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements SideMenuActions, OnInit, OnDestroy {
    groups$ !: Observable<Group[]>;
    private destroyLogout$: Subject<void> = new Subject<void>();

    constructor(private groupService: GroupService,
                private authService: AuthService,
                private utilService: UtilService,
                private sideMenuService: SideMenuService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    changePage(event: PageEvent): void {
        this.groups$ = this.groupService.getListOfGroups(event.pageIndex);
    }

    ngOnInit(): void {
        this.groups$ = this.groupService.getListOfGroups(0);
    }

    addNewGroup(): void {
        this.groupService.createNewGroup().subscribe((newGroup: Group): void => {
            this.groupService.group = newGroup;

            this.utilService.navigate(RouterPath.TASKS_DIRECT);
        });
    }

    ngOnDestroy(): void {
        this.destroyLogout$.complete();
    }

    protected readonly RouterPath = RouterPath;

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }
}
