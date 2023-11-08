import { Component, OnInit } from '@angular/core';
import { SideMenuActions } from "@core/interfaces/home/SideMenuActions";
import {
    debounceTime,
    distinctUntilChanged,
    map,
    Observable,
    of,
    startWith,
    Subject,
    switchMap,
    takeUntil
} from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { SideMenuService } from "@core/services/home/side-menu.service";
import { FormControl } from "@angular/forms";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";
import { CollaboratorService } from "@core/services/home/collaborator.service";

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements SideMenuActions, OnInit {
    private destroyLogout$: Subject<void> = new Subject<void>();
    private options !: string[];
    suggestionControl: FormControl = new FormControl<any>("");
    suggestions$ !: Observable<string[]>;
    visibleCollaborators$ !: Observable<Collaborator[]>;

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

    private filterValues(value: string): string[] {
        const filterValue: string = value.toLowerCase();

        return this.options.filter((option: string) => option.toLowerCase().includes(filterValue));
    }

    findCollaboratorByName(): void {
        let i: number = 0;

        const collaborators: Collaborator[] = this.filterValues(this.suggestionControl.value).map(
            (name: string): Collaborator => {
                return {
                    fullName: name,
                    username: `${name.replace(" ", "").toLowerCase()}@mail.com`,
                    collaboratorId: i++
                };
            }
        );
        console.log(collaborators);

        this.visibleCollaborators$ = of(collaborators);
    }

    addNewCollaborator(event: Collaborator): void {
        console.log(event);
    }
}
