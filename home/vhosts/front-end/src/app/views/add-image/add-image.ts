import { Component, OnInit }  from '@angular/core';
import { Router } from '@angular/router';
import {PictissouService} from '../../services/PictissouService';

@Component({
    templateUrl: 'add-image.component.html'
})
export class AddImageComponent implements OnInit {
    public url: String;

    constructor (private router: Router, private pict: PictissouService) { }
    ngOnInit(): void { }

    addImage() {
        this.pict.addImage(this.url);
        this.router.navigateByUrl('images');
    }
}
