import { Component, OnInit } from '@angular/core';
import {Image} from '../../models/Image';
import {PictissouService} from '../../services/PictissouService';

@Component({
    templateUrl: 'images.component.html',
    styleUrls: ['images.css']
})
export class ImagesComponent implements OnInit {

    public images: Image[];

    constructor (private pict: PictissouService) { }
    ngOnInit(): void {
        this.pict.getImages().subscribe(images => {
            this.images = images;
        });
    }
}
