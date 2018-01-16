import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { RegisterComponent } from './register';
import { RegisterRoutingModule } from './register-routing.module';
import {ToasterModule} from 'angular2-toaster';
import {FormsModule} from '@angular/forms';

@NgModule({
    imports: [
        RegisterRoutingModule,
        ChartsModule,
        BsDropdownModule,
        ToasterModule,
        FormsModule
    ],
    declarations: [ RegisterComponent ]
})
export class RegisterModule { }
