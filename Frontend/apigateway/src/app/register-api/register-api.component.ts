import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';

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
  arrResponse = new Array<any>();

  constructor(fb: FormBuilder) { 
    this.options = fb.group({
      floatLabel: 'get'
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
}
