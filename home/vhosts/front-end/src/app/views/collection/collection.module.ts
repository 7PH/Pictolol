import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import {CollectionComponent} from './collection.component';
import { CollectionRoutingModule } from './collection-routing.module';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        CollectionRoutingModule,
        ChartsModule,
        BsDropdownModule
    ],
    declarations: [ CollectionComponent ]
})
export class CollectionModule { }
