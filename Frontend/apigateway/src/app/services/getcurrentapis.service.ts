import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class GetcurrentapisService {

  constructor(private httpclient:HttpClient) {
   }

   getSavedUrls(){
    return this.httpclient.get('http://localhost:8080/hello').pipe(map(
      urlinfo =>{
        console.log(urlinfo);
        
        return urlinfo;

      }
    ))
  }
}
