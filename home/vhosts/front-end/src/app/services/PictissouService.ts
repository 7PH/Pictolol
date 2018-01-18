///<reference path="../models/User.ts"/>
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import {Image} from '../models/Image';
import {User} from '../models/User';
import {Collection} from '../models/Collection';
import {APIHelper} from './api/APIHelper';
import {APIResponse} from './api/APIResponse';
import {Category} from '../models/Category';

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

    constructor(public readonly api: APIHelper) {
        this.load();
    }

    /**
     * On page load
     */
    public load() {
        this.api.load((response: APIResponse) => {
            this.user = {
                id: response.data.idUser,
                pseudo: response.data.idUser > 0 ? response.data.pseudoUser : '*Guest'
            };
        });
    }

    /** Account */
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
        return this.api.doRequest('Users', {
            'do': 'logout'
        });
    }
    isLogged() {
        return this.user.id > 0;
    }


    /** Images */
    getImages(): Observable<Image[]> {
        return this.api.doRequest('Images', {
                'do': 'images'
            })
            .map(response => response.data as Image[]);
    }
    getImage(id: number): Observable<Image> {
        return this.api.doRequest('Images', {
            'do': 'detailimage',
            'id': id.toString()
        }).map(response => response.data as Image);
    }
    addImage(url: string, title: string, idc: number): Observable<APIResponse> {
        return this.api.doRequest('Images', {
            'do': 'addimage',
            'url': url,
            'title': title,
            'idc': idc.toString()
        });
    }
    getCategories(): Observable<Category[]> {
        return this.api.doRequest('Images', {
            'do': 'categories'
        }).map(response => response.data);
    }
    addCategory(description: string): Observable<APIResponse> {
        return this.api.doRequest('Images', {
            'do': 'addcategory',
            'description': description
        });
    }
    deleteCategory(id: number): Observable<APIResponse> {
        return this.api.doRequest('Images', {
            'do': 'deletecategory',
            'idc': id.toString()
        });
    }

    /** Collections */
    getCollections() {
        return of(PictissouService.COLLECTIONS);
    }
    getCollection(id: number) {
        return of(PictissouService.COLLECTIONS.filter(collection => { return collection.id === id; })[0]);
    }
}
