import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {PictissouService} from '../../services/PictissouService';
import {ToasterService} from 'angular2-toaster';
import {Router} from '@angular/router';

@Component({
    templateUrl: 'register.component.html',
    styleUrls: ['../../../scss/vendors/toastr/toastr.scss'],
    encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {

    public pseudo: string;
    public password: string;
    public email: string;

    constructor (private root: Router, private pict: PictissouService, private toast: ToasterService) { }
    ngOnInit(): void { }

    register() {
        this.pict.register(this.pseudo, this.password, this.email)
            .subscribe(response => {
                if (response.error) {
                    this.toast.pop('error', response.message);
                } else {
                    this.root.navigate(['login']);
                }
            });
    }
}
