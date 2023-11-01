import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        redirectTo: RouterPaths.GROUPS_PATH,
        pathMatch: "full"
    },
    {
        path: RouterPaths.TASKS_PATH,
        loadChildren: () => import("./tasks/tasks.module")
            .then(module => module.TasksModule)
    },
    {
        path: RouterPaths.COLLABORATORS_PATH,
        loadChildren: () => import("./collaborators/collaborators.module")
            .then(module => module.CollaboratorsModule)
    },
    {
        path: RouterPaths.PROFILE_PATH,
        loadChildren: () => import("./profile/profile.module")
            .then(module => module.ProfileModule)
    },
    {
        path: RouterPaths.GROUPS_PATH,
        loadChildren: () => import("./groups/groups.module")
            .then(module => module.GroupsModule)
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
