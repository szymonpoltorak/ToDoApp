import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPath } from "@enums/RouterPath";
import { HomeComponent } from "./home.component";

const routes: Routes = [
    {
        path: RouterPath.CURRENT_PATH,
        component: HomeComponent,
        children: [
            {
                path: RouterPath.CURRENT_PATH,
                redirectTo: RouterPath.GROUPS_PATH,
                pathMatch: "full"
            },
            {
                path: RouterPath.TASKS_PATH,
                loadComponent: () => import("./tasks/tasks.component")
                    .then(module => module.TasksComponent)
            },
            {
                path: RouterPath.COLLABORATORS_PATH,
                loadComponent: () => import("./collaborators/collaborators.component")
                    .then(module => module.CollaboratorsComponent)
            },
            {
                path: RouterPath.SEARCH_PATH,
                loadComponent: () => import("./search/search.component")
                    .then(module => module.SearchComponent)
            },
            {
                path: RouterPath.PROFILE_PATH,
                loadComponent: () => import("./profile/profile.component")
                    .then(module => module.ProfileComponent)
            },
            {
                path: RouterPath.GROUPS_PATH,
                loadComponent: () => import("./groups/groups.component")
                    .then(module => module.GroupsComponent)
            },
            {
                path: RouterPath.SESSIONS_PATH,
                loadComponent: () => import("./sessions/sessions.component")
                    .then(module => module.SessionsComponent)
            },
            {
                path: RouterPath.SOCIALS_PATH,
                loadComponent: () => import("./socials/socials.component")
                    .then(module => module.SocialsComponent)
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class HomeRoutingModule {
}
