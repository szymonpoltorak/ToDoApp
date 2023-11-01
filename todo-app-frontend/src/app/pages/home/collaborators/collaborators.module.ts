import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CollaboratorsRoutingModule } from './collaborators-routing.module';
import { CollaboratorsComponent } from "./collaborators.component";
import { MatButtonModule } from "@angular/material/button";
import { MatDividerModule } from "@angular/material/divider";
import { MatGridListModule } from "@angular/material/grid-list";
import { MatIconModule } from "@angular/material/icon";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatCardModule } from "@angular/material/card";
import { CollaboratorComponent } from "./collaborator/collaborator.component";
import { MatListModule } from "@angular/material/list";


@NgModule({
    declarations: [
        CollaboratorsComponent,
        CollaboratorComponent
    ],
    imports: [
        CommonModule,
        CollaboratorsRoutingModule,
        MatButtonModule,
        MatDividerModule,
        MatGridListModule,
        MatIconModule,
        MatPaginatorModule,
        MatSidenavModule,
        MatToolbarModule,
        MatCardModule,
        MatListModule
    ]
})
export class CollaboratorsModule {
}
