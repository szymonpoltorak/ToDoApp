import { Component, OnInit } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { FormValidatorService } from "@core/validators/form-validator.service";

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
    loginForm !: FormGroup;

    constructor(public loginValidatorService: FormValidatorService) {
    }

    ngOnInit(): void {
        this.loginForm = this.loginValidatorService.buildFormGroup();
    }

    submitForm(): void {

    }
}
