import { Component, OnInit, ViewChildren } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {GetcurrentapisService} from '../../services/getcurrentapis.service';
// import { myElement } from 'src/app/Models/myElement';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-searchapi',
  templateUrl: './searchapi.component.html',
  styleUrls: ['./searchapi.component.css']
})
export class SearchapiComponent implements OnInit {

  public ischecked:boolean = false;
  public URL_list:any[] =new Array();
  public attributes:any[];
  // public mapping:String="";
  public mapping:any;
  
  public call_list:any ={};
  public id_list:any[] =new Array();
  public mappings ={};
  public responseMap = {};
  public selectedList = {};
  public responseAttributeMapping = {};

  constructor(private geturlservice:GetcurrentapisService) { }

  @ViewChildren('mapping') someDivs;

  ngOnInit() {
    console.log(this.ischecked);
    this.geturlservice.getSavedUrls().subscribe(res =>{
      console.log(res);
      this.URL_list.push(res);
      console.log(this.URL_list[0]);

      this.URL_list[0].forEach((element) => {
        this.selectedList[element.id] = false;
      });
      console.log(this.selectedList);
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
    Swal.fire("proceed?","the mapping "+this.mapping+" will be added to the "+attribute,"question").then(()=>{
      // this.mappings[attribute] =this.mapping;
      this.mappings[this.mapping] = attribute;

      this.mapping ="";
    })
    
    
  }

  public addtoUrlList(item){

    for(let responseName in this.responseAttributeMapping){
      if(this.responseAttributeMapping[responseName]!=""){
        this.responseMap[this.responseAttributeMapping[responseName]]= responseName;
      }
    }
    console.log(this.responseMap);

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
    apiurlobject["mappings"] =this.responseMap;
    var id:number =item.id;
    console.log(item.id);
    this.responseMap = {};
    this.call_list[id] =apiurlobject;
    console.log(this.call_list);

    this.selectedList[item.id] = "selected";

    localStorage.setItem("call_list",JSON.stringify(this.call_list));
    Swal.fire("Success","Url added to the api call list","success");
  }

  public selectedApi(id, responseAttributes){
    this.selectedList[id]= true;
    console.log(this.selectedList);

    this.responseAttributeMapping={};
    responseAttributes.forEach(element => {
      this.responseAttributeMapping[element.attribute]="";
    });
    console.log(this.responseAttributeMapping);
  }

  public removeApi(id){
    this.selectedList[id]= false;
    this.responseAttributeMapping = {};
    console.log(this.selectedList);
  }

  public test(){
    console.log(this.responseAttributeMapping);
  }

}
