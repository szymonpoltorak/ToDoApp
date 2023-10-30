import { Component, OnInit } from '@angular/core';
import { FormValidatorService } from "@core/validators/form-validator.service";
import { AbstractControl, FormGroup } from "@angular/forms";
import { RegisterRequest } from "@core/data/auth/register-request";
import { Subject, takeUntil } from "rxjs";
import { AuthResponse } from "@core/data/auth/auth-response";
import { AuthConstants } from "@enums/auth/AuthConstants";
import { FormFieldNames } from "@enums/auth/FormFieldNames";
import { StorageKeys } from "@enums/auth/StorageKeys";
import { AuthService } from "@core/services/auth/auth.service";
import { UserService } from "@core/services/utils/user.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPaths } from "@enums/RouterPaths";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
    registerForm !: FormGroup;
    private destroyRegister$: Subject<void> = new Subject<void>();

    constructor(public formValidatorService: FormValidatorService,
                private authService: AuthService,
                private userService: UserService,
                private utilService: UtilService) {
    }

    ngOnInit(): void {
        this.registerForm = this.formValidatorService.buildRegisterFormGroup();
    }

    submitForm(): void {
        if (this.registerForm.invalid) {
            return;
        }
        const request: RegisterRequest = this.buildRegisterRequest();

        this.authService.registerUser(request)
            .pipe(takeUntil(this.destroyRegister$))
            .subscribe((data: AuthResponse): void => {
                if (data.authToken === AuthConstants.NO_TOKEN) {
                    return;
                }
                this.userService.setUserAuthentication = true;

                const username: string = this.registerForm.get(FormFieldNames.EMAIL_FIELD)?.value;

                this.utilService.addValueToStorage(StorageKeys.USERNAME, username);

                this.authService.saveData(data);

                this.utilService.navigate(RouterPaths.HOME_LOGIN_PATH);
            });
    }

    private buildRegisterRequest(): RegisterRequest {
        const registerRequest: RegisterRequest = new RegisterRequest();
        const nameGroup: AbstractControl<any, any> = this.registerForm.get(FormFieldNames.NAME_GROUP)!;
        const passwordGroup: AbstractControl<any, any> = this.registerForm.get(FormFieldNames.PASSWORD_GROUP)!;

        registerRequest.name = nameGroup.get(FormFieldNames.NAME_FIELD)!.value;

        registerRequest.surname = nameGroup.get(FormFieldNames.SURNAME_FIELD)!.value;

        registerRequest.username = this.registerForm.get(FormFieldNames.EMAIL_FIELD)!.value;

        registerRequest.password = passwordGroup.get(FormFieldNames.PASSWORD_FIELD)!.value;

        return registerRequest;
    }
}
