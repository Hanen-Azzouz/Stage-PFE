import { UserModel } from "./userModel"

export class RoleModel {
    idRole!:number    
    roleName!:string
    users!:UserModel[]
    
   }