import { NgModule } from '@angular/core';
import { Routes,
     RouterModule } from '@angular/router';

import { AddImageComponent } from './add-image';

const routes: Routes = [
  {
    path: '',
    component: AddImageComponent,
    data: {
      title: 'Dashboard'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AddImageRoutingModule {}
