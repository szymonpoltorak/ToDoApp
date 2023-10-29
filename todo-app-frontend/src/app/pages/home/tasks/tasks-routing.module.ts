import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { TasksComponent } from "./tasks.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: TasksComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TasksRoutingModule {
}
