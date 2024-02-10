import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OAuthComponent } from "./oauth.component";
import { AuthGuard } from "@core/guards/auth.guard";
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: OAuthComponent
    },
    {
        path: RouterPath.HOME_LOGIN_PATH,
        loadChildren: () => import("../home/home.module")
            .then(module => module.HomeModule),
        canActivate: [AuthGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class OAuthRoutingModule {
}
