import { Component, OnInit } from '@angular/core';
import {PictissouService} from '../pictissou.service';

@Component({
    selector: 'app-home',
    templateUrl: './home.component.html',
    styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    public images: {
        recent: Array<String>,
        popular: Array<String>
    };

    constructor(private pict: PictissouService) {
        this.images = {recent: [], popular: []};
    }

    ngOnInit() {
        this.images.recent = ['', '', '', ''];
        this.images.popular = ['', '', '', ''];
    }

}
