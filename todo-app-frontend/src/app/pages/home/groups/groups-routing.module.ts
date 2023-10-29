import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RouterPaths } from "@enums/RouterPaths";
import { GroupsComponent } from "./groups.component";

const routes: Routes = [
    {
        path: RouterPaths.CURRENT_PATH,
        component: GroupsComponent
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class GroupsRoutingModule { }
