import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { map, Observable, take } from "rxjs";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorService } from "@core/services/home/collaborator.service";
import { RouterPath } from "@enums/RouterPath";

@Component({
    selector: 'app-collaborators',
    templateUrl: './collaborators.component.html',
    styleUrls: ['./collaborators.component.scss']
})
export class CollaboratorsComponent implements SideMenuActions, OnInit {
    collaborators$ !: Observable<Collaborator[]>;

    constructor(private authService: AuthService,
                private collaboratorService: CollaboratorService,
                private sideMenuService: SideMenuService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(take(1))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    ngOnInit(): void {
        this.collaborators$ = this.collaboratorService.getListOfCollaborators();
    }

    removeCollaborator(event: number): void {
        this.collaborators$ = this.collaborators$.pipe(
            map((collaborators: Collaborator[]) => collaborators.filter(c => c.collaboratorId !== event))
        );

        this.collaboratorService.removeCollaborator(event)
            .pipe(take(1))
            .subscribe();
    }
    protected readonly RouterPath = RouterPath;

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }
}
