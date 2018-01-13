import { NgModule } from '@angular/core';
import { Routes,
     RouterModule } from '@angular/router';

import { RegisterComponent } from './register';

const routes: Routes = [
  {
    path: '',
    component: RegisterComponent,
    data: {
      title: 'Dashboard'
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RegisterRoutingModule {}
