import { Component, Input } from '@angular/core';
import { Session } from "@core/interfaces/home/Session";
import { DeviceType } from "@enums/home/DeviceType";
import { MatIconModule } from "@angular/material/icon";
import { NgIf } from "@angular/common";
import { MatDividerModule } from "@angular/material/divider";

@Component({
    selector: 'app-session',
    templateUrl: './session.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        NgIf,
        MatDividerModule
    ],
    styleUrls: ['./session.component.scss']
})
export class SessionComponent {
    @Input() session !: Session;
    protected readonly DeviceType = DeviceType;
}
