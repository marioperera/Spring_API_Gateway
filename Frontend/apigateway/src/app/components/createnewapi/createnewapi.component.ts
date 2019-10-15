import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';


@Component({
  selector: 'app-createnewapi',
  templateUrl: './createnewapi.component.html',
  styleUrls: ['./createnewapi.component.css']
})
export class CreatenewapiComponent implements OnInit {
  
  server_URL ="http://localhost:4001/query/";
  email = new FormControl('', [Validators.required]);
  request_type ="";
  public register_error:boolean= false;


  constructor(private geturlservice:GetcurrentapisService) { }

  ngOnInit() {
  }

  

  getErrorMessage() {
    return this.email.hasError('required') ? 'You must enter a value' :
        this.email.hasError('email') ? 'Not a valid email' :
            '';
  }

  getRequestType(value){
    this.request_type =value;
    console.log(this.request_type);
    
  }

  resetApis(){
    localStorage.removeItem("call_list");
    alert("resetted api list!")
  }

  registernewApi(){
    this.register_error ==false;
    console.log("register new api called");
    let request_obj ={};
    request_obj["endpoint"] =this.server_URL+this.email.value;
    request_obj["type"] =this.request_type;
    var retrievedObject = localStorage.getItem('call_list');
    console.log('retrievedObject: ', JSON.parse(retrievedObject));
    request_obj["call_list"] =JSON.parse(retrievedObject);
    console.log(JSON.stringify(request_obj));
    this.geturlservice.generatenewAPI(request_obj).subscribe(res =>{
      if(res.code=="error"){
        this.register_error =true;
      }else{
        this.register_error =false;
      }
      
    });
    
  }

}
