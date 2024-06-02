import { UserModel } from "./userModel"

export class TokenModel {
    id!:number    
    accessToken!:string
    refreshToken!:string
    loggedOut!:boolean
    user!:UserModel


   
    
   }