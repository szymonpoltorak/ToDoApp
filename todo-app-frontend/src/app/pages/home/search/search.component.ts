import { Component, OnInit } from '@angular/core';
import { debounceTime, distinctUntilChanged, map, Observable, startWith, switchMap, take } from "rxjs";
import { AuthService } from "@core/services/auth/auth.service";
import { FormControl, ReactiveFormsModule } from "@angular/forms";
import { Collaborator } from "@core/data/home/Collaborator";
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";
import { CollaboratorService } from "@core/services/home/collaborator.service";
import { MatCardModule } from "@angular/material/card";
import { MatInputModule } from "@angular/material/input";
import { MatAutocompleteModule } from "@angular/material/autocomplete";
import { MatIconModule } from "@angular/material/icon";
import { MatButtonModule } from "@angular/material/button";
import { MatListModule } from "@angular/material/list";
import { CollaboratorSuggestionComponent } from "./collaborator-suggestion/collaborator-suggestion.component";
import { AsyncPipe } from "@angular/common";

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    standalone: true,
    imports: [
        MatCardModule,
        MatInputModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatIconModule,
        MatButtonModule,
        MatListModule,
        CollaboratorSuggestionComponent,
        AsyncPipe
    ],
    styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
    suggestionControl: FormControl = new FormControl<any>("");
    suggestions$ !: Observable<string[]>;
    visibleCollaborators$ !: Observable<CollaboratorSuggestion[]>;

    constructor(private authService: AuthService,
                private collaboratorService: CollaboratorService) {
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
}
