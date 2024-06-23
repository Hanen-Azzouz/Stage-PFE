import { RoleModel } from "./roleModel"
import { TokenModel } from "./tokenModel"


export class UserModel {
    idUser!:number    
    firstName!:string
    lastName!:string
    username!:string
    password!:string
    phoneNumber!:number
    email!:string
    dateNaissance!:Date
    dateInscription!:Date
    accountNonLocked!:boolean
    role!:RoleModel
    tokens!:TokenModel[]
    }