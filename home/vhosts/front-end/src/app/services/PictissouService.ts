import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import {Image} from '../models/Image';
import {User} from '../models/User';

@Injectable()
export class PictissouService {

    static IMAGES: Image[] = [
        {id: 1, url: 'http://image.noelshack.com/fichiers/2017/10/1489162412-1465686632-jesuus-risitas.gif'},
        {id: 2, url: 'http://image.noelshack.com/fichiers/2016/47/1480064732-1467335935-jesus4.png'},
        {id: 3, url: 'http://image.noelshack.com/fichiers/2016/38/1474488637-jesus26.png'}
    ];
    static IMAGE_AI = 3;

    public user: User;

    constructor() {
        this.load();
    }

    /**
     * Load the service
     */
    private load() {
        this.user = {
            id: 123,
            pseudo: 'NewUser'
        };
    }


    register() {

    }
    login(pseudo?: String, password?: String) {
        this.user = {
            id: 123,
            pseudo: 'NewUser'
        };
    }
    logout() {
        this.user = {
            id: 0,
            pseudo: '*Guest'
        };
    }
    isLogged() {
        return this.user.id > 0;
    }



    getImages(): Observable<Image[]> {
        return of(PictissouService.IMAGES);
    }

    getImage(id: number): Observable<Image> {
        return of(PictissouService.IMAGES.filter(image => { return image.id === id; })[0]);
    }
    addImage(url: String) {
        PictissouService.IMAGES.push({
            id: ++ PictissouService.IMAGE_AI,
            url: url
        });
    }




}
