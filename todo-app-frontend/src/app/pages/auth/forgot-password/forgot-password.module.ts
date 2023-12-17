import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ForgotPasswordRoutingModule } from './forgot-password-routing.module';
import { ForgotPasswordComponent } from "./forgot-password.component";
import { AuthUtilsModule } from "../auth-utils/auth-utils.module";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatButtonModule } from "@angular/material/button";


@NgModule({
    declarations: [
        ForgotPasswordComponent
    ],
    imports: [
        CommonModule,
        ForgotPasswordRoutingModule,
        AuthUtilsModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule
    ]
})
export class ForgotPasswordModule {
}
