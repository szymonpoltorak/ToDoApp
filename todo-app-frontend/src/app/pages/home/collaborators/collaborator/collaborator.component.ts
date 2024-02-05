import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Collaborator } from "@core/data/home/Collaborator";
import { MatIconModule } from "@angular/material/icon";
import { MatListModule } from "@angular/material/list";
import { NgIf } from "@angular/common";
import { MatButtonModule } from "@angular/material/button";

@Component({
    selector: 'app-collaborator',
    templateUrl: './collaborator.component.html',
    standalone: true,
    imports: [
        MatIconModule,
        MatListModule,
        NgIf,
        MatButtonModule
    ],
    styleUrls: ['./collaborator.component.scss']
})
export class CollaboratorComponent {
    @Input() collaborator !: Collaborator;
    @Input() isAlreadyCollaborator: boolean = false;
    @Output() readonly addCollaboratorEvent: EventEmitter<Collaborator> = new EventEmitter<Collaborator>();
    @Output() readonly removeCollaboratorEvent: EventEmitter<number> = new EventEmitter<number>();

    emitRemoveCollaboratorEvent(): void {
        this.removeCollaboratorEvent.emit(this.collaborator.collaboratorId);
    }

    emitAddCollaboratorEvent(): void {
        this.addCollaboratorEvent.emit(this.collaborator);
    }
}
