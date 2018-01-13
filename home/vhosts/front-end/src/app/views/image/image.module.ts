import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import {ImageComponent} from './image.component';
import { ImageRoutingModule } from './image-routing.module';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        ImageRoutingModule,
        ChartsModule,
        BsDropdownModule
    ],
    declarations: [ ImageComponent ]
})
export class ImageModule { }
