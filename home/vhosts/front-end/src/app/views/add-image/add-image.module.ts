import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { AddImageComponent } from './add-image';
import { AddImageRoutingModule } from './add-image-routing.module';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        AddImageRoutingModule,
        ChartsModule,
        BsDropdownModule,
        FormsModule
    ],
    declarations: [ AddImageComponent ]
})
export class AddImageModule { }
