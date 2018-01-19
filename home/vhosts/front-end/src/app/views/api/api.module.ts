import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { ApiComponent } from './api';
import { ApiRoutingModule } from './api-routing.module';
import {ToasterModule} from 'angular2-toaster';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        ApiRoutingModule,
        ChartsModule,
        BsDropdownModule,
        ToasterModule,
        FormsModule
    ],
    declarations: [ ApiComponent ]
})
export class ApiModule { }
