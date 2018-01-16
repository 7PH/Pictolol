import { NgModule } from '@angular/core';
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';

import { LoginComponent } from './login';
import { LoginRoutingModule } from './login-routing.module';
import {ToasterModule} from 'angular2-toaster';
import {FormsModule} from '@angular/forms';

@NgModule({
    imports: [
        LoginRoutingModule,
        ChartsModule,
        BsDropdownModule,
        ToasterModule,
        FormsModule
    ],
    declarations: [ LoginComponent ]
})
export class LoginModule { }
