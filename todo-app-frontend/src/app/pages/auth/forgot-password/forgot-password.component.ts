import { Component, OnInit } from '@angular/core';
import { FormValidatorService } from "@core/validators/form-validator.service";
import { FormGroup } from "@angular/forms";
import { FormFieldNames } from "@enums/auth/FormFieldNames";
import { AuthService } from "@core/services/auth/auth.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { take } from "rxjs";
import { AuthApiCalls } from "@enums/auth/AuthApiCalls";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
    forgotPasswordGroup!: FormGroup;

    constructor(protected loginValidatorService: FormValidatorService,
                private utilService: UtilService,
                private authService: AuthService) {
    }

    ngOnInit(): void {
        this.forgotPasswordGroup = this.loginValidatorService.buildForgotPasswordForm();
    }

    sendEmailWithLink(): void {
        if (this.forgotPasswordGroup.invalid) {
            return;
        }
        const username: string = this.forgotPasswordGroup.get(FormFieldNames.EMAIL_FIELD)?.value;

        const dateTimeLocal: string = this.forgotPasswordGroup.get("dateTimeLocal")?.value;

        if (dateTimeLocal != undefined && dateTimeLocal != "") {
            console.error(JSON.parse(AuthApiCalls.ERROR_FOUND));

            return;
        }
        this.authService
            .sendEmailWithLink(username)
            .pipe(take(1))
            .subscribe((response: string) => this.utilService.navigate(RouterPath.LOGIN_DIRECT));
    }
}
