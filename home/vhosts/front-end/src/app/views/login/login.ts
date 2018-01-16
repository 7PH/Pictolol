import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';
import {ToasterService} from 'angular2-toaster';
import {Router} from '@angular/router';

@Component({
    templateUrl: 'login.component.html',
    styleUrls: ['../../../scss/vendors/toastr/toastr.scss'],
    encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {

    public pseudo: string;
    public password: string;

    constructor (private root: Router, private pict: PictissouService, private toast: ToasterService) { }
    ngOnInit(): void { }

    login() {
        this.pict.login(this.pseudo, this.password)
            .subscribe(response => {
                if (response.error) {
                    this.toast.pop('error', response.message);
                } else {
                    this.pict.load();
                    this.root.navigateByUrl('#');
                }
            });
    }
}
