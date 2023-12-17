import { DeviceType } from "@enums/home/DeviceType";

export interface Session {
    dateOfLogin: string;
    ipAddress: string;
    deviceType: DeviceType;
    timeOfLogin: string;
}
