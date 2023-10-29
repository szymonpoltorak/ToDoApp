import { Component, OnInit } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";
import { PageEvent } from "@angular/material/paginator";
import { Observable, of } from "rxjs";
import { Group } from "@core/data/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";

@Component({
    selector: 'app-groups',
    templateUrl: './groups.component.html',
    styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements SideNavActions, OnInit {
    groups$ !: Observable<Group[]>;

    constructor(private groupService: GroupService,
                private utilService: UtilService) {
    }

    changeToGroupsView(): void {
    }

    changeToProfileView(): void {
    }

    logoutUser(): void {
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
}
