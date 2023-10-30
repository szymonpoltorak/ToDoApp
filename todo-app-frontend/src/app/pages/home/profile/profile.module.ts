import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from "./profile.component";
import { MatButtonModule } from "@angular/material/button";
import { MatDividerModule } from "@angular/material/divider";
import { MatIconModule } from "@angular/material/icon";
import { MatSidenavModule } from "@angular/material/sidenav";
import { MatToolbarModule } from "@angular/material/toolbar";
import { MatCardModule } from "@angular/material/card";
import { MatListModule } from "@angular/material/list";


@NgModule({
    declarations: [
        ProfileComponent
    ],
    imports: [
        CommonModule,
        ProfileRoutingModule,
        MatButtonModule,
        MatDividerModule,
        MatIconModule,
        MatSidenavModule,
        MatToolbarModule,
        MatCardModule,
        MatListModule
    ]
})
export class ProfileModule {
}
