import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordFieldComponent } from './password-field/password-field.component';
import { EmailFieldComponent } from './email-field/email-field.component';
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatInputModule } from "@angular/material/input";
import { NameFieldComponent } from './name-field/name-field.component';


@NgModule({
    declarations: [
        PasswordFieldComponent,
        EmailFieldComponent,
        NameFieldComponent
    ],
    exports: [
        EmailFieldComponent,
        PasswordFieldComponent,
        NameFieldComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
    ]
})
export class AuthUtilsModule {
}
