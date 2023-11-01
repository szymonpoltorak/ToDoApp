import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { map, Observable, of, Subject, takeUntil } from "rxjs";
import { Collaborator } from "@core/data/home/Collaborator";

@Component({
    selector: 'app-collaborators',
    templateUrl: './collaborators.component.html',
    styleUrls: ['./collaborators.component.scss']
})
export class CollaboratorsComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    collaborators$ !: Observable<Collaborator[]>;

    constructor(private authService: AuthService,
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
        const collaborators: Collaborator[] = [];

        for (let i = 0; i < 10; i++) {
            collaborators.push({
               fullName: `Name-${i} Surname-${i}`,
               username: `name${i}@mail.com`,
               collaboratorId: i
            });
        }
        this.collaborators$ = of(collaborators);
    }

    removeCollaborator(event: number): void {
        this.collaborators$ = this.collaborators$.pipe(
            map((collaborators: Collaborator[]) => collaborators.filter(c => c.collaboratorId !== event))
        );
    }
}
