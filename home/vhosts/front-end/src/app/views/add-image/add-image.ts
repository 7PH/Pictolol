import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {PictissouService} from '../../services/PictissouService';
import {Category} from '../../models/Category';

@Component({
    templateUrl: 'add-image.component.html'
})
export class AddImageComponent implements OnInit {
    public url: string;
    public categories: Category[];
    public category: number;
    public description: string;

    constructor (private router: Router, private pict: PictissouService) { }
    ngOnInit(): void {
        this.pict.getCategories()
            .subscribe(categories => {
                this.categories = categories;
                this.category = this.categories[0].id;
            });
    }

    addImage() {
        this.pict.addImage(this.url, this.description, this.category)
            .subscribe(response => {
                if (response.error) {
                    this.description = response.message;
                } else {
                    this.router.navigateByUrl('images');
                }
            });
    }
}
