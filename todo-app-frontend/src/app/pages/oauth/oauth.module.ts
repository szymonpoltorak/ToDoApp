import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { OAuthRoutingModule } from './oauth-routing.module';
import { OAuthComponent } from "./oauth.component";
import { MatProgressSpinnerModule } from "@angular/material/progress-spinner";


@NgModule({
    declarations: [
        OAuthComponent
    ],
    exports: [
        OAuthComponent
    ],
    imports: [
        CommonModule,
        OAuthRoutingModule,
        MatProgressSpinnerModule
    ]
})
export class OAuthModule {
}
