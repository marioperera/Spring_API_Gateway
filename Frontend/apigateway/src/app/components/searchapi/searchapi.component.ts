import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';

@Component({
  selector: 'app-searchapi',
  templateUrl: './searchapi.component.html',
  styleUrls: ['./searchapi.component.css']
})
export class SearchapiComponent implements OnInit {

  public ischecked:boolean = false;
  public URL_list:any[] =new Array();

  constructor(private geturlservice:GetcurrentapisService) { }

  ngOnInit() {
    console.log(this.ischecked);
    this.geturlservice.getSavedUrls();
    
  }
  email = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'You must enter a value' :
        this.email.hasError('email') ? 'Not a valid email' :
            '';
  }

  public addtoUrlList(){
    console.log("log to console called");
    
    console.log(this.ischecked);
    this.geturlservice.getSavedUrls();
    
  }

}
