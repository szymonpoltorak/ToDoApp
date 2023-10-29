import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { GroupsRoutingModule } from './groups-routing.module';
import { GroupsComponent } from "./groups.component";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatGridListModule } from "@angular/material/grid-list";
import { GroupComponent } from './group/group.component';


@NgModule({
    declarations: [
        GroupsComponent,
        GroupComponent
    ],
    imports: [
        CommonModule,
        GroupsRoutingModule,
        MatSidenavModule,
        MatDividerModule,
        MatIconModule,
        MatToolbarModule,
        MatButtonModule,
        MatPaginatorModule,
        MatGridListModule
    ]
})
export class GroupsModule {
}
