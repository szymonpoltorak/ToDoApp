import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { LoginComponent } from "./login.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: LoginComponent
    },
    {
        path: RouterPath.REGISTER_FULL_PATH,
        loadChildren: () => import("../register/register.module")
            .then(module => module.RegisterModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LoginRoutingModule {
}
