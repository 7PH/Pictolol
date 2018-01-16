///<reference path="../models/User.ts"/>
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import {Image} from '../models/Image';
import {User} from '../models/User';
import {Collection} from '../models/Collection';
import {APIHelper} from './api/APIHelper';
import {APIResponse} from './api/APIResponse';

@Injectable()
export class PictissouService {

    static IMAGES: Image[] = [
        {id: 1, url: 'http://image.noelshack.com/fichiers/2017/10/1489162412-1465686632-jesuus-risitas.gif'},
        {id: 2, url: 'http://image.noelshack.com/fichiers/2016/47/1480064732-1467335935-jesus4.png'},
        {id: 3, url: 'http://image.noelshack.com/fichiers/2016/38/1474488637-jesus26.png'}
    ];
    static IMAGE_AI = 3;
    static COLLECTIONS: Collection[] = [
        {id: 1, likeImage: 0, user: 1, description: 'Humoristique'},
        {id: 2, likeImage: 0, user: 1, description: 'NSFW'},
    ];

    public user: User = {id: 0, pseudo: ''};

    constructor(private api: APIHelper) {
        this.load();
    }

    /**
     * Load the service
     */
    public load() {
        this.api.load((response: APIResponse) => {
            this.user = {
                id: response.data.idUser,
                pseudo: response.data.idUser > 0 ? response.data.pseudoUser : '*Guest'
            };
        });
    }


    register(pseudo: string, password: string, email: string) {
        return this.api.doRequest('Users', {
            'do': 'register',
            'pseudo': pseudo,
            'password': password,
            'email': email
        });
    }
    login(pseudo: string, password: string): Observable<APIResponse> {
        return this.api.doRequest('Users', {
            'do': 'login',
            'pseudo': pseudo,
            'password': password
        });
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
    addImage(url: string) {
        PictissouService.IMAGES.push({
            id: ++ PictissouService.IMAGE_AI,
            url: url
        });
    }


    getCollections() {
        return of(PictissouService.COLLECTIONS);
    }

    getCollection(id: number) {
        return of(PictissouService.COLLECTIONS.filter(collection => { return collection.id === id; })[0]);
    }
}
