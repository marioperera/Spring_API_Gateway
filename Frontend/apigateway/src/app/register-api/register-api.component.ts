import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

import {RegisterApi} from '../Models/registerApi';
import {RegisterApiService} from "../Services/register-api.service";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-register-api',
  templateUrl: './register-api.component.html',
  styleUrls: ['./register-api.component.css']
})
export class RegisterApiComponent implements OnInit {

  options: FormGroup;
  requestParamNo:0;
  reponseParamNo:0;
  arr = new Array<any>();
  requestValues = new Array<any>();
  requestParameters = new Array<any>();
  requestTypes = new Array<any>();
  responseParameters = new Array<any>();
  responseTypes = new Array<any>();
  arrResponse = new Array<any>();

  requestObj = {};
  responseObj = {};

  registerApi: RegisterApi = new RegisterApi();

  constructor(fb: FormBuilder, private registerApiService: RegisterApiService) { 
    this.options = fb.group({
      floatLabel: 'GET'
    });
  }

  ngOnInit() {
  }

  addInput(): void{
    for(let i=0; i<this.requestParamNo; i++){
      this.arr[i] = i;
    }
  }

  addInputResponse(): void{
    for(let i=0; i<this.reponseParamNo; i++){
      this.arrResponse[i] = i;
    }
  }

  registerApiSubmit():void{
    this.registerApi.type = this.options.value.floatLabel;
    this.registerApi.requestValues = this.requestValues;

    for(let i=0; i<this.requestParameters.length; i++){
      this.requestObj[this.requestParameters[i]] =  this.requestTypes[i];
    }
    
    for(let i=0; i<this.responseParameters.length; i++){
      this.responseObj[this.responseParameters[i]] = this.responseTypes[i];
    }
    this.registerApi.requestparams = this.requestObj;
    this.registerApi.responseparams = this.responseObj;
    console.log(this.registerApi);

    this.registerApiService.createApi(this.registerApi).subscribe(
      data=> console.log(data),
      error=>{if(error.error.text == "Already regitered url"){
        Swal.fire('Oops...', 'Already Registered Api!', 'error');
      }
      else if(error.error.text == "Successfully register"){
        Swal.fire('Success', "Successfully Registered",'success');
      }
      else if(error.error.text == "Bad Request" || error.error.error=="Internal Server Error"){
        Swal.fire('Warning','Bad Api. Verifiaction failed', 'warning');
      }
      console.log(error)
    });
  }
}
