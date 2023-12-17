import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ResetPasswordRoutingModule } from './reset-password-routing.module';
import { ResetPasswordComponent } from "./reset-password.component";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { AuthUtilsModule } from "../auth-utils/auth-utils.module";
import { MatButtonModule } from "@angular/material/button";


@NgModule({
    declarations: [
        ResetPasswordComponent
    ],
    imports: [
        CommonModule,
        ResetPasswordRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        AuthUtilsModule,
        MatButtonModule
    ]
})
export class ResetPasswordModule {
}
