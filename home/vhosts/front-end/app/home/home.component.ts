import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';

@Component({
selector: 'app-home',
templateUrl: './home.component.html',
styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
    private resultData: String = '';

    constructor(private http: HttpClient) { }

    ngOnInit() {
        this.resultData = 'This content has been added on runtime';
    }

}
