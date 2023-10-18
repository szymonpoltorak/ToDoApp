import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { AuthGuard } from "@core/guards/auth.guard";

const routes: Routes = [
    {
        path: RouterPaths.AUTH_PATH,
        loadChildren: () => import("./pages/auth/auth.module")
            .then(module => module.AuthModule)
    },
    {
        path: RouterPaths.HOME_PATH,
        loadChildren: () => import("./pages/home/home.module")
            .then(module => module.HomeModule),
        canActivate: [AuthGuard]
    },
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: "auth/login",
        pathMatch: 'full'
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
