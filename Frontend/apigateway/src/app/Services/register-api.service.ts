import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterApi } from '../Models/registerApi';
import { RegisterNewQueryApi } from '../Models/registerNewQueryApi';

@Injectable({
  providedIn: 'root'
})
export class RegisterApiService {

  constructor(private http: HttpClient) { }

  createApi(registerApi: RegisterApi): Observable<any>{
    return this.http.post('http://localhost:4001/api/addApi',registerApi);
  }

  getAllApi():Observable<any>{
    return this.http.get('http://localhost:4001/api/getAllAPi');
  }

  createNewQueryApi( registerNewQueryApi : RegisterNewQueryApi): Observable<any>{
    console.log(registerNewQueryApi);
    return this.http.post('http://localhost:4001/api/testRegister', registerNewQueryApi);

  }
}
