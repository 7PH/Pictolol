import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AddImageComponent } from './add-image';
import { DashboardRoutingModule } from './add-image-routing.module';

@NgModule({
  imports: [
    DashboardRoutingModule,
    ChartsModule,
    BsDropdownModule
  ],
  declarations: [ AddImageComponent ]
})
export class AddImageModule { }
