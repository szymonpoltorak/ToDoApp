import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        redirectTo: RouterPath.LOGIN_AUTH_PATH,
        pathMatch: 'full'
    },
    {
        path: RouterPath.LOGIN_AUTH_PATH,
        loadChildren: () => import('./login/login.module')
            .then(module => module.LoginModule),
    },
    {
        path: RouterPath.REGISTER_AUTH_PATH,
        loadChildren: () => import("./register/register.module")
            .then(module => module.RegisterModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class AuthRoutingModule {
}
