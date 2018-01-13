import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import {
  FullLayoutComponent,
  SimpleLayoutComponent
} from './containers';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'images',
        pathMatch: 'full',
    },
    {
        path: '',
        component: FullLayoutComponent,
        data: {
            title: 'Accueil'
        },
        children: [
            {
                path: 'images',
                loadChildren: './views/images/images.module#ImagesModule'
            },
            {
                path: 'add-image',
                loadChildren: './views/add-image/add-image.module#AddImageModule'
            },
            {
                path: 'login',
                loadChildren: './views/login/login.module#LoginModule'
            },
            {
                path: 'register',
                loadChildren: './views/register/register.module#RegisterModule'
            },
            {
                path: 'image/:id',
                loadChildren: './views/image/image.module#ImageModule'
            }
        ]
    }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
