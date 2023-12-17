import { Component, Input } from '@angular/core';
import { Session } from "@core/interfaces/home/Session";
import { DeviceType } from "@enums/home/DeviceType";

@Component({
  selector: 'app-session',
  templateUrl: './session.component.html',
  styleUrls: ['./session.component.scss']
})
export class SessionComponent {
    @Input() session !: Session;
    protected readonly DeviceType = DeviceType;
}
