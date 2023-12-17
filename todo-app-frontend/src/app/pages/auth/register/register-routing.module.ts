import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { RegisterComponent } from "./register.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: RegisterComponent
    },
    {
        path: RouterPath.LOGIN_FULL_PATH,
        loadChildren: () => import("../login/login.module")
            .then(module => module.LoginModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class RegisterRoutingModule {
}
