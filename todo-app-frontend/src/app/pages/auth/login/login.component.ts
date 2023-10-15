import { Component, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { LoginValidatorService } from "@core/validators/login-validator.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm !: FormGroup;

    constructor(public loginValidatorService: LoginValidatorService) {
    }

    ngOnInit(): void {
        this.loginForm = this.loginValidatorService.buildFormGroup();
    }

    submitForm(): void {

    }
}
