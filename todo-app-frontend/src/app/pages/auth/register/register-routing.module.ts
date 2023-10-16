import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { RegisterComponent } from "./register.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: RegisterComponent
    },
    {
        path: RouterPaths.LOGIN_FULL_PATH,
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
