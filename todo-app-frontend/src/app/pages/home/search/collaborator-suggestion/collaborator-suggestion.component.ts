import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CollaboratorSuggestion } from "@core/data/home/CollaboratorSuggestion";

@Component({
  selector: 'app-collaborator-suggestion',
  templateUrl: './collaborator-suggestion.component.html',
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
