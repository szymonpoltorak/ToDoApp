import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TasksRoutingModule } from './tasks-routing.module';
import { TasksComponent } from "./tasks.component";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatButtonModule } from "@angular/material/button";


@NgModule({
    declarations: [
        TasksComponent
    ],
    imports: [
        CommonModule,
        TasksRoutingModule,
        MatSidenavModule,
        MatDividerModule,
        MatIconModule,
        MatToolbarModule,
        MatButtonModule
    ]
})
export class TasksModule {
}
