import { Component, OnInit, ViewChildren } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';
// import { myElement } from 'src/app/Models/myElement';

@Component({
  selector: 'app-searchapi',
  templateUrl: './searchapi.component.html',
  styleUrls: ['./searchapi.component.css']
})
export class SearchapiComponent implements OnInit {

  public ischecked:boolean = false;
  public URL_list:any[] =new Array();
  public attributes:any[];
  public mapping:String="";
  public call_list:any ={};
  public id_list:any[] =new Array();
  public mappings ={};

  constructor(private geturlservice:GetcurrentapisService) { }

  @ViewChildren('mapping') someDivs;

  ngOnInit() {
    console.log(this.ischecked);
    this.geturlservice.getSavedUrls().subscribe(res =>{
      console.log(res);
      this.URL_list.push(res);
      console.log(this.URL_list[0]);
      
      
    });
    
  }

  ngAfterViewInit(){

  }

 

  email = new FormControl('', [Validators.required]);

  getErrorMessage() {
    return this.email.hasError('required') ? 'You must enter a value' :
        this.email.hasError('email') ? 'Not a valid email' :
            '';
  }

  public changeidtominus(item,attribute_id){
    console.log("item added");
    
    setTimeout(()=>{
      attribute_id.id =-1;
    })
    
    // console.log(item);
    // console.log(attribute_id);
    
    
  }

  public registervalues(attribute){
    console.log(attribute);
    
    // var myElement = document.getElementById( 'mapping-'+event );
    // console.log(myElement.nodeValue.toString);
    
    
  }



  public addmapping(attribute){
    console.log('mappings-'+attribute);    
    var myElement = (<HTMLInputElement>document.getElementById('mappings-'+attribute));
    console.log(myElement.value);
    this.mapping =myElement.value;
    console.log(this.mapping,attribute);
    this.mappings[attribute] =this.mapping;
    this.mapping ="";
    
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
    let apiurlobject ={};
    apiurlobject["response_attribs"] =this.attributes;
    apiurlobject["mappings"] =this.mappings;
    var id:number =item.id;
    console.log(item.id);
    this.mappings = {};
    

    this.call_list[id] =apiurlobject;
    

    
    console.log(this.call_list);
    localStorage.setItem("call_list",JSON.stringify(this.call_list));
    
    
    
  }

}
