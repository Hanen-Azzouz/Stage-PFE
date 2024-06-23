import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/userModel';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class UserService {
 // public $refreshToken=new Subject<boolean>;
 
  constructor(private http :HttpClient) {}


  registerUser(user:UserModel){
    
    return this.http.post<UserModel>("http://localhost:8082/hospital/auth/register",user);

  }

    loginUser(user:UserModel){
      return this.http.post("http://localhost:8082/hospital/auth/login",user);}

      refreshToken(obj:any){
        return this.http.post<any>("http://localhost:8082/hospital/auth/refresh_token",obj)
      }

    getAllUsers(){
      return this.http.get<UserModel[]>("http://localhost:8082/hospital/user/getAllUsers");
      }
      getOneUserById(id:any){
        return this.http.get<UserModel>("http://localhost:8082/hospital/user/getUserById/"+id); }
        
    updateUser(idUser:number,user:UserModel){
        
      return this.http.put<UserModel>("http://localhost:8082/hospital/user/updateUser/"+idUser,user);}
    
    lockUserAccount(username:string){
      return this.http.put<any>("http://localhost:8082/hospital/user/lockUserAccount",username);
    }
    unlockUserAccount(username:string){
      return this.http.put<any>("http://localhost:8082/hospital/user/unlockUserAccount",username);
    }

    deleteUser(id:number){

      return this.http.delete<any>("http://localhost:8082/hospital/user/deleteUserById/"+id);
    }

    getActifsUsers(status:boolean){

      return this.http.get<UserModel[]>("http://localhost:8082/hospital/user/getActifUsers/"+status);
    }
    getUserByUsername(username:string){

      return this.http.get<UserModel>("http://localhost:8082/hospital/user/getUserByUsername/"+username);
    }

    getUserByDateInscriptionBetween(date1:Date,date2:Date){
      return this.http.get<UserModel[]>("http://localhost:8082/hospital/user/getUserByDateInscriptionBetween/"+date1+"/"+date2);
    }
    changePwd(currentPassword:string,newPassword:string,confirmationPassword:string):Observable<any>{
      const body= { currentPassword, newPassword,confirmationPassword };

      return this.http.patch<any>("http://localhost:8082/hospital/user/changePwd",body)
    }
    getPWDExpired(username:string){

      return this.http.get<boolean>("http://localhost:8082/hospital/user/isPWDExpired/"+username)
    }








}
