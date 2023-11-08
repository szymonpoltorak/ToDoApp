import { Component, OnDestroy, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { map, Observable, Subject, takeUntil } from "rxjs";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorService } from "@core/services/home/collaborator.service";

@Component({
    selector: 'app-collaborators',
    templateUrl: './collaborators.component.html',
    styleUrls: ['./collaborators.component.scss']
})
export class CollaboratorsComponent implements SideMenuActions, OnInit, OnDestroy {
    private destroyLogout$: Subject<void> = new Subject<void>();
    private destroyDeleteGroup$: Subject<void> = new Subject<void>();
    collaborators$ !: Observable<Collaborator[]>;

    constructor(private authService: AuthService,
                private collaboratorService: CollaboratorService,
                private sideMenuService: SideMenuService) {
    }

    changeToGroupsView(): void {
        this.sideMenuService.changeToGroupsView();
    }

    changeToProfileView(): void {
        this.sideMenuService.changeToProfileView();
    }

    changeToCollaboratorsView(): void {
        this.sideMenuService.changeToCollaboratorsView();
    }

    changeToSearchView(): void {
        this.sideMenuService.changeToSearchView();
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
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
            .pipe(takeUntil(this.destroyDeleteGroup$))
            .subscribe();
    }

    ngOnDestroy(): void {
        this.destroyDeleteGroup$.complete();
    }
}
