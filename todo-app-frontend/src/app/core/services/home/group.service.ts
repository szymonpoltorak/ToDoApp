import { Injectable } from '@angular/core';
import { Group } from "@core/data/home/Group";

@Injectable({
    providedIn: 'root'
})
export class GroupService {
    private _group !: Group;

    get group() {
        return this._group;
    }

    set group(group: Group) {
        this._group = group;
    }
}
