import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { CategoriesComponent } from './categories';
import { CategoriesRoutingModule } from './categories-routing.module';
import {ToasterModule} from 'angular2-toaster';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';

@NgModule({
    imports: [
        CommonModule,
        CategoriesRoutingModule,
        ChartsModule,
        BsDropdownModule,
        ToasterModule,
        FormsModule
    ],
    declarations: [ CategoriesComponent ]
})
export class CategoriesModule { }
