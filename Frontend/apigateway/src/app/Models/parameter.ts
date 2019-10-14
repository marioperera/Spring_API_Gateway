export class parameter{
    id:number;
    paramname:String;
    type:String;

    
    constructor(init?:Partial<parameter>){
        Object.assign(this,init)
    }
}