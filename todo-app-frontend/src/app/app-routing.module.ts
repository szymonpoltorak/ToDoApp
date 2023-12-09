import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { AuthGuard } from "@core/guards/auth.guard";

const routes: Routes = [
    {
        path: RouterPath.AUTH_PATH,
        loadChildren: () => import("./pages/auth/auth.module")
            .then(module => module.AuthModule)
    },
    {
        path: RouterPath.HOME_PATH,
        loadChildren: () => import("./pages/home/home.module")
            .then(module => module.HomeModule),
        canActivate: [AuthGuard]
    },
    {
        path: RouterPath.CURRENT_PATH,
        redirectTo: "auth/login",
        pathMatch: 'full'
    },
    {
        path: "**",
        redirectTo: "/auth/login",
        pathMatch: "full"
    }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
