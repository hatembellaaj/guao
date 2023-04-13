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
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
