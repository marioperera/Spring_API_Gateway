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
  email = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'You must enter a value' :
        this.email.hasError('email') ? 'Not a valid email' :
            '';
  }

  public changeidtominus(item,attribute_id){
    attribute_id.id =-1;
    // console.log(item);
    // console.log(attribute_id);
    
    
  }

  public addtoUrlList(item){
    // console.log("log to console called");
    // console.log(item);
    this.attributes=new Array();
    
    // this.call_list = JSON.parse(localStorage.getItem("call_list"))
    item.responseAttributes.forEach(attrib => {
      if(attrib.id ==-1){
        this.attributes.push(attrib.attribute)
        
      }
    });
    // console.log(this.attributes);
    
    var id:number =item.id;
    console.log(item.id);
    this.call_list[id] =this.attributes;
    

    
    console.log(this.call_list);
    localStorage.setItem("call_list",JSON.stringify(this.call_list));
    
    
  }

  public addSelectApiList(item){
    this.selectedAPi.push(item);
    console.log(this.selectedAPi);
  }

  public submitForm():void{
    this.registerNewQueryApi.type = "POST";
    for(let i=0; i<this.selectedAPi.length; i++){
      let responseArr:any =[];
      let responseArrObject:any={};
      for(let j=0; j<this.selectedAPi[i].responseAttributes.length; j++){
        responseArr[j]= this.selectedAPi[i].responseAttributes[j].attribute;
      }
      responseArrObject["response_attribs"] = responseArr;
      this.call_list[this.selectedAPi[i].url] = responseArrObject;
    }
    console.log("This is call_list");
    console.log(this.call_list);
    this.registerNewQueryApi.endpoint = "http://localhost:4001/query/"+this.queryurl;
    this.registerNewQueryApi.call_list = this.call_list;
    
    this.registerApiService.createNewQueryApi(this.registerNewQueryApi).subscribe(
      data=>{
        Swal.fire('Success', "Successfully Registered",'success');
        // window.location.reload();
        console.log(data);
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
