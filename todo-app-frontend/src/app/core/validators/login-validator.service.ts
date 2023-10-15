import { Injectable } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { FormValidation } from "@enums/FormValidation";

@Injectable({
    providedIn: 'root'
})
export class LoginValidatorService {
    emailControl: FormControl = new FormControl(
        FormValidation.EMAIL_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.EMAIL_PATTERN)
        ]
    );

    passwordControl: FormControl = new FormControl(
        FormValidation.PASSWORD_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.PASSWORD_PATTERN),
        ]
    );

    constructor(private formBuilder: FormBuilder) {
    }

    buildFormGroup(): FormGroup {
        return this.formBuilder.group({
            email: this.emailControl,
            password: this.passwordControl
        });
    }
}
