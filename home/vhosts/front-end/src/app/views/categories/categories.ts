import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';
import {ToasterService} from 'angular2-toaster';
import {Router} from '@angular/router';
import {CategoriesRoutingModule} from './categories-routing.module';
import {Category} from '../../models/Category';

@Component({
    templateUrl: 'categories.component.html',
    styleUrls: ['../../../scss/vendors/toastr/toastr.scss'],
    encapsulation: ViewEncapsulation.None
})
export class CategoriesComponent implements OnInit {

    public categories: Category[];
    public newCategoryDescription: string;

    constructor (private root: Router, private pict: PictissouService) { }
    ngOnInit(): void {
        this.pict.getCategories()
            .subscribe(categories => {
                this.categories = categories;
            });
    }

    deleteCategory(category: Category) {
        this.pict.deleteCategory(category.id)
            .subscribe(response => {
                this.ngOnInit();
            });
    }
    addCategory() {
        this.pict.addCategory(this.newCategoryDescription)
            .subscribe(response => {
                this.ngOnInit();
            });
    }

}
