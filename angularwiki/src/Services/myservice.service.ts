import { Injectable } from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';
/**
 * @description
 * @class
 */
@Injectable()
export class MyserviceService {
 private headers = new Headers({
   'Content-Type': 'application/x-www-form-urlencoded', 'Accept': 'application/json',
   'Access-Control-Allow-Origin': 'http://localhost:4200',
   'Access-Control-Allow-Credentials': 'true', 'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS'});



  constructor(private http: Http) {

  }

  getdata(name: any): Promise<any> {
    return this.http.post('http://localhost:8080/WikiFinder/wiki/hardik', {headers: this.headers})
               .toPromise()
               .then(response => response.json().data).catch(this.handleError);

  }
  private handleError(error: any): Promise<any> {
    console.error('An error occurred', error); // for demo purposes only
    return Promise.reject(error.message || error);
  }
}
