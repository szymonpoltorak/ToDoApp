import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from "@angular/forms";

@Injectable({
    providedIn: 'root'
})
export class PasswordValidatorService {
    constructor() {
    }

    passwordMatchValidator(passwordFieldName: string, repeatFieldName: string): ValidatorFn {
        return (control: AbstractControl): { [key: string]: any } | null => {
            const userPassword: AbstractControl<any, any> | null = control.get(passwordFieldName);
            const repeatPassword: AbstractControl<any, any> | null = control.get(repeatFieldName);

            if (!userPassword || !repeatPassword) {
                return null;
            }
            return userPassword.value !== repeatPassword.value ? {'passwordMatch': true} : null;
        };
    }
}
