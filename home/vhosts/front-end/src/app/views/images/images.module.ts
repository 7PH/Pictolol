import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import {ImagesComponent} from './images.component';
import { ImagesRoutingModule } from './images-routing.module';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        ImagesRoutingModule,
        ChartsModule,
        BsDropdownModule
    ],
    declarations: [ ImagesComponent ]
})
export class ImagesModule { }
