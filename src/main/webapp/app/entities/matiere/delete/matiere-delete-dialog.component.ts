import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMatiere } from '../matiere.model';
import { MatiereService } from '../service/matiere.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './matiere-delete-dialog.component.html',
})
export class MatiereDeleteDialogComponent {
  matiere?: IMatiere;

  constructor(protected matiereService: MatiereService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.matiereService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
