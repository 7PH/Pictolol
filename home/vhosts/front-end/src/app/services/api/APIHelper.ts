import {APIResponse} from './APIResponse';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {APIRequest} from './APIRequest';
import {Injectable} from '@angular/core';
import {RequestOptions} from '@angular/http';


@Injectable()
export class APIHelper {

    public static readonly API_HOST = 'jee.risibank.fr';
    public static readonly API_PORT = 80;
    public static readonly API_PATH = '/pictissou/';

    private csrfName = '';
    private csrfValue = '';

    constructor(private http: HttpClient) {}

    public setCsrf(name: string, value: string): void {
        this.csrfName = name;
        this.csrfValue = value;
    }

    public doRequest(route: String, data: {[s: string]: string; }): Observable<APIResponse> {
        let params: String = '';
        for (const key in data) {
            if (data.hasOwnProperty(key)) {
                params += key + '=' + data[key] + '&';
            }
        }
        params += this.csrfName + '=' + this.csrfValue;
        return this.http.post<APIResponse>('http://' + APIHelper.API_HOST + ':' + APIHelper.API_PORT + APIHelper.API_PATH + route, params, {
            headers: new HttpHeaders()
                .set('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
        });
    }

    public load(callback?: (response: APIResponse) => any) {
        this.doRequest('Users', {'do': 'load'})
            .subscribe(response => {
                this.setCsrf(response.data.csrfName, response.data.csrfValue);
                if (callback != null) {
                    callback(response);
                }
            });
    }
}
