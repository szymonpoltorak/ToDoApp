import { Component, OnInit } from '@angular/core';
import { FormValidatorService } from "@core/validators/form-validator.service";
import { FormGroup } from "@angular/forms";
import { FormFieldNames } from "@enums/auth/FormFieldNames";
import { AuthService } from "@core/services/auth/auth.service";

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent implements OnInit {
    forgotPasswordGroup!: FormGroup;
    constructor(protected loginValidatorService: FormValidatorService,
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

        console.log(username);
    }
}
