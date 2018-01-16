import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

// Import Containers
import {
  FullLayoutComponent,
  SimpleLayoutComponent
} from './containers';
import {CollectionModule} from './views/collection/collection.module';

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
            },
            {
                path: 'images',
                loadChildren: './views/images/images.module#ImagesModule'
            },
            {
                path: 'add-image',
                loadChildren: './views/add-image/add-image.module#AddImageModule'
            },
            {
                path: 'collections',
                loadChildren: './views/collections/collections.module#CollectionsModule'
            },
            {
                path: 'collection/:id',
                loadChildren: './views/collection/collection.module#CollectionModule'
            },
        ]
    }
];

@NgModule({
  imports: [ RouterModule.forRoot(routes) ],
  exports: [ RouterModule ]
})
export class AppRoutingModule {}
