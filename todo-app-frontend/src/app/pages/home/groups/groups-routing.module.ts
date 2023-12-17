import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { GroupsComponent } from "./groups.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: GroupsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class GroupsRoutingModule {
}
