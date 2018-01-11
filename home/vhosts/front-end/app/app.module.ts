import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { CguComponent } from './cgu/cgu.component';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule, MatToolbarModule, MatIconModule, MatCardModule, MatMenuModule, MatTabsModule} from '@angular/material';
import {PictissouService} from './pictissou.service';
import { CollectionsComponent } from './collections/collections.component';
import { NotfoundComponent } from './notfound/notfound.component';

const appRoutes: Routes = [
    { path: '', component: HomeComponent },
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
        NotfoundComponent
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
        HttpClientModule
    ],
    providers: [PictissouService],
    bootstrap: [AppComponent]
})
export class AppModule { }
