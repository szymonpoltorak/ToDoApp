import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { SessionsComponent } from "./sessions.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: SessionsComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class SessionsRoutingModule {
}
