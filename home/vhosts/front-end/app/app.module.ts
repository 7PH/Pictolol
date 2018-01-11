import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { CguComponent } from './cgu/cgu.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule, MatToolbarModule, MatIconModule, MatCardModule, MatMenuModule, MatTabsModule, MatInputModule} from '@angular/material';
import {PictissouService} from './pictissou.service';
import { CollectionsComponent } from './collections/collections.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { LoginComponent } from './login/login.component';
import { AddImageComponent } from './add-image/add-image.component';
import { RegisterComponent } from './register/register.component';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'images', component: HomeComponent },
    { path: 'add-image', component: AddImageComponent },
    { path: 'collections', component: CollectionsComponent},
    { path: 'cgu', component: CguComponent },
    { path: '**', component: NotfoundComponent }
];


RouterModule.forRoot([ ]);

@NgModule({
    declarations: [
        AppComponent,
        HomeComponent,
        CguComponent,
        CollectionsComponent,
        NotfoundComponent,
        LoginComponent,
        AddImageComponent,
        RegisterComponent
    ],
    imports: [
        RouterModule.forRoot(
            appRoutes,
            { enableTracing: true } // <-- debugging purposes only
        ),
        BrowserModule,
        BrowserAnimationsModule,
        MatMenuModule,
        MatButtonModule,
        MatToolbarModule,
        MatIconModule,
        MatCardModule,
        MatTabsModule,
        MatInputModule,
        HttpClientModule
    ],
    providers: [PictissouService],
    bootstrap: [AppComponent]
})
export class AppModule { }
