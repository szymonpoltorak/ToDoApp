import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.LOGIN_AUTH_PATH,
        pathMatch: 'full'
    },
    {
        path: RouterPaths.LOGIN_AUTH_PATH,
        loadChildren: () => import('./login/login.module')
            .then(module => module.LoginModule),
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
