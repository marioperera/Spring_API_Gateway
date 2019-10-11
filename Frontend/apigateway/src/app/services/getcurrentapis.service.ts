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
     console.log("service called");
     
    return this.httpclient.get('http://localhost:4001/Utils/getregistedURLs').pipe(map(
      urlinfo =>{
        console.log(urlinfo);
        
        return urlinfo;

      }
    ))
  }
}
