import { Component, OnInit } from '@angular/core';
import { Observable, of, take } from "rxjs";
import { User } from "@core/data/home/User";
import { ProfileService } from "@core/services/home/profile.service";
import { UtilService } from "@core/services/utils/util.service";
import { RouterPath } from "@enums/RouterPath";
import { MatCardModule } from "@angular/material/card";
import { MatListModule } from "@angular/material/list";
import { AsyncPipe, CommonModule } from "@angular/common";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { SocialPlatform } from "@enums/home/SocialPlatform";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    standalone: true,
    imports: [
        MatCardModule,
        MatListModule,
        AsyncPipe,
        MatIconModule,
        CommonModule,
        MatButtonModule
    ],
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    user$ !: Observable<User>;

    constructor(private profileService: ProfileService,
                private utilService: UtilService) {
    }

    closeAccount(): void {
        this.profileService.closeAccount()
            .pipe(take(1))
            .subscribe((): void => {
                this.utilService.clearStorage();

                this.utilService.navigate(RouterPath.LOGIN_DIRECT);
            });
    }

    ngOnInit(): void {
        this.user$ = this.profileService.getUserData();
    }

    removeAccount(socialAccountId: number): void {
        this.user$ = this.profileService.removeSocialAccount(socialAccountId);
    }
}
