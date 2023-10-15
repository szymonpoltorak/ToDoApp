import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

const routes: Routes = [
    {
        path: "auth",
        loadChildren: () => import("./pages/auth/auth.module")
            .then(module => module.AuthModule)
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
