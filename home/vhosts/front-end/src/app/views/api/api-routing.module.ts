import { NgModule } from '@angular/core';
import { Routes,
     RouterModule } from '@angular/router';

import { ApiComponent } from './api';

const routes: Routes = [
  {
    path: '',
    component: ApiComponent,
    data: {
      title: 'Dashboard'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ApiRoutingModule {}
