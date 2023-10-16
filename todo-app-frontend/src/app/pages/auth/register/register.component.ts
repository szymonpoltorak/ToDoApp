import { Component, OnInit } from '@angular/core';
import { FormValidatorService } from "@core/validators/form-validator.service";
import { FormGroup } from "@angular/forms";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    registerGroup !: FormGroup;

    constructor(public formValidatorService: FormValidatorService) {
    }

    ngOnInit(): void {
        this.registerGroup = this.formValidatorService.buildRegisterFormGroup();
    }

    submitForm(): void {

    }
}
