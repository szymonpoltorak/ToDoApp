import { Component, Input } from '@angular/core';
import { Collaborator } from "@core/data/home/Collaborator";

@Component({
  selector: 'app-collaborator',
  templateUrl: './collaborator.component.html',
  styleUrls: ['./collaborator.component.scss']
})
export class CollaboratorComponent {
    @Input() collaborator !: Collaborator;
}
