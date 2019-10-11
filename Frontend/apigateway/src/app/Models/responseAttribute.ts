export class responseAttribute{
    id:number;
    attribute:String;
    type:String;

    
    constructor(init?:Partial<responseAttribute>){
        Object.assign(this,init)
    }
}