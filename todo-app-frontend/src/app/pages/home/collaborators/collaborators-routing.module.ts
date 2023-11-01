import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { CollaboratorsComponent } from "./collaborators.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: CollaboratorsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CollaboratorsRoutingModule {
}
