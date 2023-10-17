import { Injectable } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { FormValidation } from "@enums/auth/FormValidation";
import { PasswordValidatorService } from "@core/validators/password-validator.service";

@Injectable({
    providedIn: 'root'
})
export class FormValidatorService {
    readonly nameControl: FormControl = new FormControl(FormValidation.NAME_VALUE,
        [
            Validators.required,
            Validators.maxLength(FormValidation.NAME_MAX_LENGTH),
            Validators.minLength(FormValidation.NAME_MIN_LENGTH),
            Validators.pattern(FormValidation.NAME_PATTERN)
        ]
    );

    readonly surnameControl: FormControl = new FormControl(FormValidation.NAME_VALUE,
        [
            Validators.required,
            Validators.maxLength(FormValidation.NAME_MAX_LENGTH),
            Validators.minLength(FormValidation.NAME_MIN_LENGTH),
            Validators.pattern(FormValidation.NAME_PATTERN)
        ]
    );

    readonly emailControl: FormControl = new FormControl(
        FormValidation.EMAIL_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.EMAIL_PATTERN)
        ]
    );

    readonly passwordControl: FormControl = new FormControl(
        FormValidation.PASSWORD_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.PASSWORD_PATTERN),
        ]
    );

    readonly repeatPasswordControl: FormControl = new FormControl(
        FormValidation.PASSWORD_VALUE,
        [
            Validators.required,
            Validators.pattern(FormValidation.PASSWORD_PATTERN),
        ]
    );
    private readonly passwordName: string = "userPassword";
    private readonly repeatPassword: string = "repeatPassword";

    constructor(private formBuilder: FormBuilder,
                private passwordValidator: PasswordValidatorService) {
    }

    buildFormGroup(): FormGroup {
        return this.formBuilder.group({
            email: this.emailControl,
            password: this.passwordControl
        });
    }

    buildRegisterFormGroup(): FormGroup {
        return this.formBuilder.group({
            nameInputs: this.formBuilder.group({
                firstName: this.nameControl,
                lastName: this.surnameControl
            }),
            email: this.emailControl,
            passwordInputs: this.formBuilder.group({
                    userPassword: this.passwordControl,
                    repeatPassword: this.repeatPasswordControl
                },
                {
                    validator: this.passwordValidator.passwordMatchValidator(this.passwordName, this.repeatPassword)
                }
            )
        });
    }
}
