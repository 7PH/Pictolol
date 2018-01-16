import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import {CollectionsComponent} from './collections.component';
import { CollectionsRoutingModule } from './collections-routing.module';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        CollectionsRoutingModule,
        ChartsModule,
        BsDropdownModule
    ],
    declarations: [ CollectionsComponent ]
})
export class CollectionsModule { }
