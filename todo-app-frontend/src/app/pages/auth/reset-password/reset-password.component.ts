import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormGroup } from "@angular/forms";
import { FormValidatorService } from "@core/validators/form-validator.service";
import { FormFieldNames } from "@enums/auth/FormFieldNames";
import { ResetPassword } from "@core/data/auth/reset-password";
import { ActivatedRoute } from "@angular/router";
import { AuthService } from "@core/services/auth/auth.service";
import { take } from "rxjs";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { AuthApiCalls } from "@enums/auth/AuthApiCalls";

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
                private utilService: UtilService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.resetPasswordForm = this.formValidatorService.buildResetPasswordFormGroup();
        this.activatedRoute.queryParams
            .pipe(take(1))
            .subscribe(params => {
                this.resetPasswordToken = params['token'];

                if (this.resetPasswordToken == undefined || this.resetPasswordToken == "") {
                    this.utilService.navigate(RouterPath.LOGIN_DIRECT);
                }
            });
    }

    submitForm(): void {
        if (this.resetPasswordForm.invalid) {
            return;
        }
        const request: ResetPassword = this.buildResetPasswordRequest();

        const date: string = this.resetPasswordForm.get("date")?.value;

        if (date != undefined && date != "") {
            console.error(JSON.parse(AuthApiCalls.ERROR_FOUND));

            return;
        }
        this.authService
            .resetPassword(request)
            .subscribe((response: string) => this.utilService.navigate(RouterPath.LOGIN_DIRECT));
    }

    private buildResetPasswordRequest(): ResetPassword {
        const passwordGroup: AbstractControl<any, any> = this.resetPasswordForm.get(FormFieldNames.PASSWORD_GROUP)!;

        const password = passwordGroup.get(FormFieldNames.PASSWORD_FIELD)!.value;

        return {
            newPassword: password,
            resetPasswordToken: this.resetPasswordToken
        };
    }
}
