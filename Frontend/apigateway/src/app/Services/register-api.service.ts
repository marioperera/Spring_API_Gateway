import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RegisterApi } from '../Models/registerApi';

@Injectable({
  providedIn: 'root'
})
export class RegisterApiService {

  constructor(private http: HttpClient) { }

  createApi(registerApi: RegisterApi): Observable<any>{
    return this.http.post('http://localhost:4001/api/addApi',registerApi);
  }
}
