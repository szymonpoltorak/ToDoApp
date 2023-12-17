import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { SearchComponent } from "./search.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: SearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SearchRoutingModule {
}
