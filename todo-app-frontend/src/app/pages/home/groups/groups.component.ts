import { Component, OnInit } from '@angular/core';
import { MatPaginatorModule, PageEvent } from "@angular/material/paginator";
import { Observable } from "rxjs";
import { Group } from "@core/data/home/Group";
import { GroupService } from "@core/services/home/group.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { AuthService } from "@core/services/auth/auth.service";
import { MatGridListModule } from "@angular/material/grid-list";
import { GroupComponent } from "./group/group.component";
import { MatDividerModule } from "@angular/material/divider";
import { AsyncPipe, CommonModule } from "@angular/common";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";

@Component({
    selector: 'app-groups',
    templateUrl: './groups.component.html',
    standalone: true,
    imports: [
        MatGridListModule,
        GroupComponent,
        MatDividerModule,
        MatPaginatorModule,
        AsyncPipe,
        CommonModule,
        MatIconModule,
        MatButtonModule
    ],
    styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements  OnInit {
    groups$ !: Observable<Group[]>;

    constructor(private groupService: GroupService,
                private authService: AuthService,
                private utilService: UtilService) {
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
}
