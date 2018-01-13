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

    constructor (private root: Router, private pict: PictissouService, private toast: ToasterService) { }
    ngOnInit(): void { }

    login() {
        this.pict.login();
        // this.toast.pop('success', 'Vous êtes connecté, ' + this.pict.user.pseudo);
        this.root.navigateByUrl('#');
    }
}
