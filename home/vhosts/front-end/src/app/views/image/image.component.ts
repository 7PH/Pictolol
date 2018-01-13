import {Component, OnDestroy, OnInit} from '@angular/core';
import {Image} from '../../models/Image';
import {PictissouService} from '../../services/PictissouService';
import {ActivatedRoute} from '@angular/router';

@Component({
    templateUrl: 'image.component.html',
    styleUrls: ['image.css']
})
export class ImageComponent implements OnInit, OnDestroy {

    public image: Image;
    private sub: any;

    constructor (private route: ActivatedRoute, private pict: PictissouService) { }

    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            this.pict.getImage(+params['id']).subscribe(image => {
                this.image = image;
            });
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}
