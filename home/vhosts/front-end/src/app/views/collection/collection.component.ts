import {Component, OnDestroy, OnInit} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';
import {Collection} from '../../models/Collection';
import {ActivatedRoute} from '@angular/router';

@Component({
    templateUrl: 'collection.component.html'
})
export class CollectionComponent implements OnInit, OnDestroy {

    public collection: Collection;
    private sub: any;

    constructor (private route: ActivatedRoute, private pict: PictissouService) { }

    ngOnInit() {
        this.sub = this.route.params.subscribe(params => {
            this.pict.getCollection(+params['id']).subscribe(collection => {
                this.collection = collection;
            });
        });
    }

    ngOnDestroy() {
        this.sub.unsubscribe();
    }
}

