import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'classe',
        data: { pageTitle: 'Classes' },
        loadChildren: () => import('./classe/classe.module').then(m => m.ClasseModule),
      },
      {
        path: 'eleve',
        data: { pageTitle: 'Eleves' },
        loadChildren: () => import('./eleve/eleve.module').then(m => m.EleveModule),
      },
      {
        path: 'matiere',
        data: { pageTitle: 'Matieres' },
        loadChildren: () => import('./matiere/matiere.module').then(m => m.MatiereModule),
      },
      {
        path: 'examen',
        data: { pageTitle: 'Examen' },
        loadChildren: () => import('./examen/examen.module').then(m => m.ExamenModule),
      },
      {
        path: 'absence',
        data: { pageTitle: 'Absences' },
        loadChildren: () => import('./absence/absence.module').then(m => m.AbsenceModule),
      },
      {
        path: 'note',
        data: { pageTitle: 'Notes' },
        loadChildren: () => import('./note/note.module').then(m => m.NoteModule),
      },
      {
        path: 'inscrption',
        data: { pageTitle: 'Inscrptions' },
        loadChildren: () => import('./inscrption/inscrption.module').then(m => m.InscrptionModule),
      },
      {
        path: 'paiement',
        data: { pageTitle: 'Paiements' },
        loadChildren: () => import('./paiement/paiement.module').then(m => m.PaiementModule),
      },
      {
        path: 'professeur',
        data: { pageTitle: 'Professeurs' },
        loadChildren: () => import('./professeur/professeur.module').then(m => m.ProfesseurModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
