import { Injectable } from "@angular/core";
import { UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { GroupService } from "@core/services/home/group.service";
import { RouterPaths } from "@enums/RouterPaths";
import { UtilService } from "@core/services/utils/util.service";

@Injectable({
    providedIn: 'root'
})
export class TasksGuard {
    constructor(private groupService: GroupService,
                private utilService: UtilService) {
    }

    canActivate(): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        if (this.groupService.group == undefined) {
            this.utilService.navigate(RouterPaths.GROUPS_DIRECT);
        }
        return true;
    }
}
