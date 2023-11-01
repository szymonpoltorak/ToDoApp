import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Collaborator } from "@core/data/home/Collaborator";

@Component({
    selector: 'app-collaborator',
    templateUrl: './collaborator.component.html',
    styleUrls: ['./collaborator.component.scss']
})
export class CollaboratorComponent {
    @Input() collaborator !: Collaborator;
    @Output() readonly removeCollaboratorEvent: EventEmitter<number> = new EventEmitter<number>();

    emitRemoveCollaboratorEvent(): void {
        this.removeCollaboratorEvent.emit(this.collaborator.collaboratorId);
    }
}
