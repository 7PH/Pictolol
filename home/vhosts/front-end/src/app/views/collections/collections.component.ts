import { Component, OnInit } from '@angular/core';
import {Image} from '../../models/Image';
import {PictissouService} from '../../services/PictissouService';
import {Collection} from '../../models/Collection';

@Component({
    templateUrl: 'collections.component.html',
    styleUrls: ['collections.css']
})
export class CollectionsComponent implements OnInit {

    public collections: Collection[];

    constructor (private pict: PictissouService) { }
    ngOnInit(): void {
        this.pict.getCollections().subscribe(collections => {
            this.collections = collections;
        });
    }
}
