import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormGroup } from "@angular/forms";
import { FormValidatorService } from "@core/validators/form-validator.service";
import { FormFieldNames } from "@enums/auth/FormFieldNames";
import { ResetPassword } from "@core/data/auth/reset-password";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "@core/services/auth/auth.service";
import { take } from "rxjs";

@Component({
    selector: 'app-reset-password',
    templateUrl: './reset-password.component.html',
    styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {
    resetPasswordForm !: FormGroup;
    resetPasswordToken !: string;

    constructor(public formValidatorService: FormValidatorService,
                private activatedRoute: ActivatedRoute,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.resetPasswordForm = this.formValidatorService.buildResetPasswordFormGroup();
        this.activatedRoute.queryParams
            .pipe(take(1))
            .subscribe(params => this.resetPasswordToken = params['token']);
    }

    submitForm(): void {
        if (this.resetPasswordForm.invalid) {
            return;
        }
        const request: ResetPassword = this.buildResetPasswordRequest();

        console.log(request);
    }

    private buildResetPasswordRequest(): ResetPassword {
        const passwordGroup: AbstractControl<any, any> = this.resetPasswordForm.get(FormFieldNames.PASSWORD_GROUP)!;

        const password = passwordGroup.get(FormFieldNames.PASSWORD_FIELD)!.value;

        return {
            resetPassword: password,
            resetPasswordToken: this.resetPasswordToken
        };
    }
}
