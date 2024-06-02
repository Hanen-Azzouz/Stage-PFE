import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserModel } from '../models/userModel';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  
  constructor(private http :HttpClient) { }






  registerUser(user:UserModel){
    
    return this.http.post<UserModel>("http://localhost:8082/hospital/auth/register",user);

  }

  loginUser(user:UserModel){
    return this.http.post("http://localhost:8082/hospital/auth/login",user);}

  getAllUsers(){
    return this.http.get<UserModel[]>("http://localhost:8082/hospital/user/getAllUsers");
    }
    getOneUserById(id:any){
      return this.http.get<UserModel>("http://localhost:8082/hospital/user/getUserById/"+id); }
      
  updateUser(user:UserModel){
    return this.http.put<UserModel>("http://localhost:8082/hospital/user/updateUser/",user);}
   
  













}
