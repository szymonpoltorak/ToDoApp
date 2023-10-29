import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.TASKS_PATH,
        pathMatch: "full"
    },
    {
        path: RouterPaths.TASKS_PATH,
        loadChildren: () => import("./tasks/tasks.module")
            .then(module => module.TasksModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
