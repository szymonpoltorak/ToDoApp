import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { CollaboratorsComponent } from "./collaborators.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: CollaboratorsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CollaboratorsRoutingModule {
}
