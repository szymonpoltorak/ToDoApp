import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from "rxjs";
import { ActivatedRoute } from "@angular/router";
import { UtilService } from "@core/services/utils/util.service";
import { StorageKeys } from "@enums/auth/StorageKeys";
import { RouterPath } from "@enums/RouterPath";
import { UserService } from "@core/services/utils/user.service";

@Component({
    selector: 'app-oauth',
    templateUrl: './oauth.component.html',
    styleUrls: ['./oauth.component.scss']
})
export class OAuthComponent implements OnInit, OnDestroy {
    private destroyRoute$: Subject<void> = new Subject<void>();

    constructor(private activatedRoute: ActivatedRoute,
                private utilService: UtilService,
                private userService: UserService) {
    }

    ngOnInit(): void {
        this.activatedRoute.queryParams
            .pipe(takeUntil(this.destroyRoute$))
            .subscribe(params => {
                const authToken = params['authToken'];
                const refreshToken = params['refreshToken'];

                this.utilService.addValueToStorage(StorageKeys.AUTH_TOKEN, authToken);
                this.utilService.addValueToStorage(StorageKeys.REFRESH_TOKEN, refreshToken);

                if (this.userService.isUserAuthenticated) {
                    this.utilService.navigate(RouterPath.HOME_LOGIN_PATH);
                } else {
                    this.utilService.navigate(RouterPath.LOGIN_DIRECT);
                }
            });
    }

    ngOnDestroy(): void {
        this.destroyRoute$.next();
        this.destroyRoute$.complete();
    }
}
