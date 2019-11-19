import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';
import { response } from 'src/app/Models/response';
import Swal from 'sweetalert2';
import {RegisterApiService} from '../../Services/register-api.service';

@Component({
  selector: 'app-createnewapi',
  templateUrl: './createnewapi.component.html',
  styleUrls: ['./createnewapi.component.css']
})
export class CreatenewapiComponent implements OnInit {
  
  server_URL ="http://localhost:4001/api/query/";
  email = new FormControl('', [Validators.required]);
  request_type ="";
  public register_error:boolean= false;


  constructor(private geturlservice:GetcurrentapisService, private registerApiService:RegisterApiService) { }

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

    if(this.request_type=="POST"){
      console.log("This is post");
      this.geturlservice.generatenewAPI(request_obj).subscribe((data:response) =>{
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
    }
    else if(this.request_type=="GET"){
      this.geturlservice.generatenewAPI(request_obj).subscribe((res:response) =>{
        try{
          if(res.code=="error"){
            this.register_error =true;
            Swal.fire("Oops..! "," couldnt register api ",'error');
            window.location.reload();
            
          }else{
            this.register_error =false;
            Swal.fire("Success"," New Api Created!! ",'success');
          }
        }
        catch(error){
          
        }
   
      });
    }
  }

}
