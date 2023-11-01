import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { SearchComponent } from "./search.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: SearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SearchRoutingModule {
}
