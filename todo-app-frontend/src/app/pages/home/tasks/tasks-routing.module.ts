import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { TasksComponent } from "./tasks.component";
import { TasksGuard } from "@core/guards/tasks.guard";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: TasksComponent,
        canActivate: [TasksGuard]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TasksRoutingModule {
}
