import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import {
    debounceTime,
    distinctUntilChanged,
    map,
    Observable,
    startWith,
    Subject,
    switchMap,
    take,
    takeUntil
} from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { FormControl } from "@angular/forms";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";
import { CollaboratorService } from "@core/services/home/collaborator.service";
import { RouterPath } from "@enums/RouterPath";

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    suggestionControl: FormControl = new FormControl<any>("");
    suggestions$ !: Observable<string[]>;
    visibleCollaborators$ !: Observable<CollaboratorSuggestion[]>;

    constructor(private authService: AuthService,
                private collaboratorService: CollaboratorService,
                private sideMenuService: SideMenuService) {
    }

    logoutUser(): void {
        this.authService.logoutUser()
            .pipe(takeUntil(this.destroyLogout$))
            .subscribe(() => this.sideMenuService.logoutUser());
    }

    ngOnInit(): void {
        this.suggestions$ = this.suggestionControl.valueChanges.pipe(
            startWith(""),
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((option: string) => this.fetchSuggestions(option))
        );
    }

    private fetchSuggestions(option: string): Observable<string[]> {
        const options$: Observable<CollaboratorSuggestion[]> = this.collaboratorService.getSuggestions(option);

        return options$.pipe(
            map(suggestions => {
                const options: string[] = [];

                for (let suggestion of suggestions) {
                    options.push(suggestion.fullName);
                }
                return options;
            })
        );
    }

    findCollaboratorByName(): void {
        this.visibleCollaborators$ = this.collaboratorService.getSuggestions(this.suggestionControl.value);
    }

    addNewCollaborator(event: CollaboratorSuggestion): void {
        this.collaboratorService.addCollaborator(event.username)
            .pipe(take(1))
            .subscribe((data: Collaborator) => {
                this.visibleCollaborators$ = this.visibleCollaborators$.pipe(
                    map(c => c.filter(item => item.username !== data.username))
                );
            });
    }

    changeRouteToNewView(route: RouterPath): void {
        this.sideMenuService.changeRouteToNewView(route);
    }

    protected readonly RouterPath = RouterPath;
}
