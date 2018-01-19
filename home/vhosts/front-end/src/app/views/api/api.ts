import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';
import {ToasterService} from 'angular2-toaster';
import {Router} from '@angular/router';
import {APIRequest} from '../../services/api/APIRequest';

@Component({
    templateUrl: 'api.component.html',
    styleUrls: ['../../../scss/vendors/toastr/toastr.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ApiComponent implements OnInit {

    public servlet = 'Users';
    public operation = 'load';
    public result = '{empty}';

    public paramCount = 5;
    public paramNames: string[] = [];
    public paramValues: string[] = [];

    constructor (private root: Router, private pict: PictissouService) { }
    ngOnInit(): void {
        for (let i = 0; i < this.paramCount; i ++) {
            this.paramNames[i] = '';
            this.paramValues[i] = '';
        }
    }

    public send() {
        const data = {'do': this.operation};

        for (let i = 0; i < this.paramCount; i ++) {
            if (this.paramNames[i] !== '') {
                data[this.paramNames[i]] = this.paramValues[i];
            }
        }
        this.pict.api.doRequest(this.servlet, data)
            .subscribe(response => {
                    this.result = JSON.stringify(response, null, '\t');
                },
                error => {
                    this.result = 'ERROR' + '\n' + JSON.stringify(error, null, '\t');
                });
    }
}
