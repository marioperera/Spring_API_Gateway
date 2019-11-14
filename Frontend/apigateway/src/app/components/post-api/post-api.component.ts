import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';
import {RegisterApiService} from '../../Services/register-api.service';
import {RegisterNewQueryApi} from '../../Models/registerNewQueryApi';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-post-api',
  templateUrl: './post-api.component.html',
  styleUrls: ['./post-api.component.css']
})
export class PostApiComponent implements OnInit {

  public ischecked:boolean = false;
  public URL_list:any[] =new Array();
  public attributes:any[];

  public selectedAPi:any[] = new Array();
 
  public call_list:any ={};
  public id_list:any[] =new Array();
  public queryurl:String;

  registerNewQueryApi:RegisterNewQueryApi = new  RegisterNewQueryApi();

  constructor(private geturlservice:GetcurrentapisService, private registerApiService:RegisterApiService) { }

  ngOnInit() {
    console.log(this.ischecked);
    this.geturlservice.getSavedUrls().subscribe(res =>{
      console.log(res);
      this.URL_list.push(res);
      console.log(this.URL_list[0]); 
    });
    
  }

  //Selected api list
  public addSelectApiList(item){
    this.selectedAPi.push(item);
    console.log(this.selectedAPi);
  }

  //submit selected api with new api
  public submitForm():void{

    this.registerNewQueryApi.type = "POST";

    for(let i=0; i<this.selectedAPi.length; i++){

      let responseArr:any =[];
      let responseArrObject:any={};

      for(let j=0; j<this.selectedAPi[i].responseAttributes.length; j++){

        responseArr[j]= this.selectedAPi[i].responseAttributes[j].attribute;

      }

      responseArrObject["response_attribs"] = responseArr;

      //concate url with type ex:"http://localhost:4001/some-POST"
      this.call_list[this.selectedAPi[i].url+"-"+this.selectedAPi[i].type] = responseArrObject;

    }

    console.log("This is call_list");
    console.log(this.call_list);
    this.registerNewQueryApi.endpoint = "http://localhost:4001/query/"+this.queryurl;
    this.registerNewQueryApi.call_list = this.call_list;
    
    this.registerApiService.createNewQueryApi(this.registerNewQueryApi).subscribe(
      data=>{
        console.log("register data");
        console.log(data);
        if(data.code=="error"){
          Swal.fire('Opps', "This endpoint is already used",'error');
        }
        else{
          Swal.fire('Success', "Successfully Registered",'success');

        }
      },
      error=>{

        Swal.fire('Opps', "Something went wrong",'error');
        console.log(error);
        
      }

    )
    console.log(this.call_list);
    console.log(this.registerNewQueryApi.endpoint);
  }

  public removeSelectedApi(index):void{
    this.selectedAPi.splice(index,1);
  }

}
