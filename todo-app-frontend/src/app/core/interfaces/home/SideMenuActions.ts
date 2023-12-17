import { RouterPath } from "@enums/RouterPath";

export interface SideMenuActions {
    changeRouteToNewView(route: RouterPath): void;

    logoutUser(): void;
}
