import { NgModule } from '@angular/core';
import { Routes,
     RouterModule } from '@angular/router';

import { CategoriesComponent } from './categories';

const routes: Routes = [
  {
    path: '',
    component: CategoriesComponent,
    data: {
      title: 'Dashboard'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriesRoutingModule {}
