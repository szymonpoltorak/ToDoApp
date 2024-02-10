import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";
import { MatIconModule } from "@angular/material/icon";
import { NgIf } from "@angular/common";
import { MatListModule } from "@angular/material/list";
import { MatButtonModule } from "@angular/material/button";

@Component({
    selector: 'app-collaborator-suggestion',
    templateUrl: './collaborator-suggestion.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        NgIf,
        MatListModule,
        MatButtonModule
    ],
    styleUrls: ['./collaborator-suggestion.component.scss']
})
export class CollaboratorSuggestionComponent {
    @Input() collaborator !: CollaboratorSuggestion;
    @Input() isAlreadyCollaborator: boolean = false;
    @Output() readonly addCollaboratorEvent: EventEmitter<CollaboratorSuggestion> = new EventEmitter<CollaboratorSuggestion>();

    emitAddCollaboratorEvent(): void {
        this.addCollaboratorEvent.emit(this.collaborator);
    }
}
