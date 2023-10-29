import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})
export class GroupService {
    private _groupId: number = -1;

    constructor() {
    }

    get groupId() {
        return this._groupId;
    }

    set groupId(groupId: number) {
        this._groupId = groupId;
    }
}
