import { Component, OnDestroy, OnInit } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";
import { PageEvent } from "@angular/material/paginator";
import { Observable, of, Subject, takeUntil } from "rxjs";
import { Group } from "@core/data/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";

@Component({
    selector: 'app-groups',
    templateUrl: './groups.component.html',
    styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements SideNavActions, OnInit, OnDestroy {
    private destroyLogout$: Subject<void> = new Subject<void>();
    groups$ !: Observable<Group[]>;

    constructor(private groupService: GroupService,
                private authService: AuthService,
                private utilService: UtilService,
                private sideMenuService: SideMenuService) {
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

    changePage(event: PageEvent): void {
    }

    ngOnInit(): void {
        const groups: Group[] = [];

        for (let i = 0; i < 16; i++) {
            groups.push({
                groupId: i,
                groupName: `Group ${i}`
            });
        }
        this.groups$ = of(groups);
    }

    addNewGroup(): void {
        this.groupService.group = {
            groupName: "New Group",
            groupId: -1
        };
        this.utilService.navigate(RouterPaths.TASKS_DIRECT);
    }

    ngOnDestroy(): void {
        this.destroyLogout$.complete();
    }
}
