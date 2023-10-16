import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegisterRoutingModule } from './register-routing.module';
import { RegisterComponent } from "./register.component";
import { AuthUtilsModule } from "../auth-utils/auth-utils.module";
import { MatButtonModule } from "@angular/material/button";
import { ReactiveFormsModule } from "@angular/forms";


@NgModule({
    declarations: [
        RegisterComponent
    ],
    imports: [
        CommonModule,
        RegisterRoutingModule,
        AuthUtilsModule,
        MatButtonModule,
        ReactiveFormsModule
    ]
})
export class RegisterModule {
}
