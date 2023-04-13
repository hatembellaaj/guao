import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IInscrption } from '../inscrption.model';
import { InscrptionService } from '../service/inscrption.service';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';

@Component({
  templateUrl: './inscrption-delete-dialog.component.html',
})
export class InscrptionDeleteDialogComponent {
  inscrption?: IInscrption;

  constructor(protected inscrptionService: InscrptionService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.inscrptionService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
