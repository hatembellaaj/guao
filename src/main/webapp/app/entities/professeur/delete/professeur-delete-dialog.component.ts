import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IProfesseur } from '../professeur.model';
import { ProfesseurService } from '../service/professeur.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './professeur-delete-dialog.component.html',
})
export class ProfesseurDeleteDialogComponent {
  professeur?: IProfesseur;

  constructor(protected professeurService: ProfesseurService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.professeurService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
