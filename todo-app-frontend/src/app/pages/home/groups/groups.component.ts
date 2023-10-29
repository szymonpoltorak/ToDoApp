import { Component, OnInit } from '@angular/core';
import { SideNavActions } from "@core/interfaces/home/SideNavActions";
import { PageEvent } from "@angular/material/paginator";
import { Observable, of } from "rxjs";
import { Group } from "@core/data/Group";

@Component({
    selector: 'app-groups',
    templateUrl: './groups.component.html',
    styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements SideNavActions, OnInit {
    groups$ !: Observable<Group[]>;

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
}
