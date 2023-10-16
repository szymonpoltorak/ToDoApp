import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from "./login.component";
import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { ReactiveFormsModule } from "@angular/forms";
import { AuthUtilsModule } from "../auth-utils/auth-utils.module";


@NgModule({
    declarations: [
        LoginComponent
    ],
    imports: [
        CommonModule,
        LoginRoutingModule,
        MatInputModule,
        MatButtonModule,
        ReactiveFormsModule,
        AuthUtilsModule,
    ],
    providers: []
})
export class LoginModule {
}
