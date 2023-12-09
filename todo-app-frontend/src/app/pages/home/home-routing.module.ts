import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        redirectTo: RouterPath.GROUPS_PATH,
        pathMatch: "full"
    },
    {
        path: RouterPath.TASKS_PATH,
        loadChildren: () => import("./tasks/tasks.module")
            .then(module => module.TasksModule)
    },
    {
        path: RouterPath.COLLABORATORS_PATH,
        loadChildren: () => import("./collaborators/collaborators.module")
            .then(module => module.CollaboratorsModule)
    },
    {
        path: RouterPath.SEARCH_PATH,
        loadChildren: () => import("./search/search.module")
            .then(module => module.SearchModule)
    },
    {
        path: RouterPath.PROFILE_PATH,
        loadChildren: () => import("./profile/profile.module")
            .then(module => module.ProfileModule)
    },
    {
        path: RouterPath.GROUPS_PATH,
        loadChildren: () => import("./groups/groups.module")
            .then(module => module.GroupsModule)
    },
    // {
    //     path: RouterPath.SESSIONS_PATH,
    //     loadChildren: () => import("./sessions/sessions.module")
    //         .then(module => module.SessionsModule)
    // }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
