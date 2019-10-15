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
  public attributes:any[];
 
  public call_list:any ={};
  public id_list:any[] =new Array();

  constructor(private geturlservice:GetcurrentapisService) { }

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

}
