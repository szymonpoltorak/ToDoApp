import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { HomeComponent } from "./home.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: HomeComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
