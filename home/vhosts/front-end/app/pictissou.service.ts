import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {User} from './models/User';
import {of} from 'rxjs/observable/of';

@Injectable()
export class PictissouService {

    public user: User;

    constructor(private http: HttpClient) {

        // load current logged user
        this.getCurrentUser().subscribe(user => {
            this.user = user;
        });
    }

    public signOut() {
        const user: User = new User();
        user.id = 0;
        user.pseudo = '';
        this.user = user;
    }

    public isLogged(): boolean {
        return this.user.id > 0;
    }

    public getCurrentUser() {
        // return this.http.get<User>('/User');
        const user: User = new User();
        user.id = 1;
        user.pseudo = 'Guest';
        return of(user);
    }
}
