import { Component, OnInit } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatFormFieldModule } from "@angular/material/form-field";
import { MatSelectModule } from "@angular/material/select";
import { FormBuilder, FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";
import { SocialPlatform } from "@enums/home/SocialPlatform";
import { MatButtonModule } from "@angular/material/button";
import { MatIconModule } from "@angular/material/icon";
import { CommonModule } from "@angular/common";
import { MatInputModule } from "@angular/material/input";
import { SocialAccountRequest } from "@core/data/home/SocialAccountRequest";
import { MatSnackBar, MatSnackBarModule } from "@angular/material/snack-bar";
import { ProfileService } from "@core/services/home/profile.service";
import { SocialAccount } from "@core/data/home/SocialAccount";

@Component({
    selector: 'app-socials',
    templateUrl: './socials.component.html',
    standalone: true,
    imports: [
        MatCardModule,
        MatFormFieldModule,
        MatSelectModule,
        FormsModule,
        MatButtonModule,
        MatIconModule,
        CommonModule,
        MatInputModule,
        ReactiveFormsModule,
        MatSnackBarModule
    ],
    styleUrls: ['./socials.component.scss']
})
export class SocialsComponent implements OnInit {
    protected readonly Object = Object;
    protected readonly SocialPlatform = SocialPlatform;
    protected socialFormGroup !: FormGroup;

    constructor(private formBuilder: FormBuilder,
                private snackbar: MatSnackBar,
                private profileService: ProfileService) {
    }

    ngOnInit(): void {
        this.socialFormGroup = this.formBuilder.group({
            socialName: new FormControl<string>("", [
                Validators.maxLength(30),
                Validators.minLength(2),
                Validators.required
            ]),
            socialLink: new FormControl<string>("", [
                Validators.maxLength(30),
                Validators.minLength(2),
                Validators.required
            ]),
            socialPlatform: new FormControl<string>("", [
                Validators.required
            ])
        });
    }

    addNewSocialAccount(): void {
        if (this.socialFormGroup.invalid) {
            return;
        }
        const socialRequest: SocialAccountRequest = {
            socialPlatform: this.socialFormGroup.controls["socialPlatform"].value,
            socialName: this.socialFormGroup.controls["socialName"].value,
            socialLink: this.socialFormGroup.controls["socialLink"].value
        };

        if (!socialRequest.socialLink.includes(socialRequest.socialPlatform.toLowerCase())) {
            this.snackbar.open("Your link is not appropriate for chosen platform!", "Consent", {
                horizontalPosition: "center",
                verticalPosition: "bottom",
            });

            return;
        }
        this.profileService
            .addNewSocialAccount(socialRequest)
            .subscribe((data: SocialAccount) => {
                this.snackbar.open(`Your social account at ${ data.socialPlatform } has been added!`, "Ok", {
                    horizontalPosition: "center",
                    verticalPosition: "bottom",
                });
            });
    }
}
