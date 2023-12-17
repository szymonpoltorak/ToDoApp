import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SessionsRoutingModule } from './sessions-routing.module';
import { SessionsComponent } from "./sessions.component";
import { CollaboratorsModule } from "../collaborators/collaborators.module";
import { MatButtonModule } from "@angular/material/button";
import { MatCardModule } from "@angular/material/card";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatListModule } from "@angular/material/list";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbarModule } from "@angular/material/toolbar";
import { SessionComponent } from './session/session.component';
import { MatPaginatorModule } from "@angular/material/paginator";


@NgModule({
    declarations: [
        SessionsComponent,
        SessionComponent
    ],
    imports: [
        CommonModule,
        SessionsRoutingModule,
        CollaboratorsModule,
        MatButtonModule,
        MatCardModule,
        MatDividerModule,
        MatIconModule,
        MatListModule,
        MatSidenavModule,
        MatToolbarModule,
        MatPaginatorModule
    ]
})
export class SessionsModule {
}
