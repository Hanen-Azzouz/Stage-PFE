import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import * as CryptoJS from 'crypto-js';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private token: string | null = null;

  private secretKey: string = '3b764a14b47285436cf8ebf16c1738689db9b192b97562b40732789cdd6eb688'; // Use the same key as the server

  constructor(private http:HttpClient) { }

  decodeToken(token:string):any{

    try{if (!token || typeof token !== 'string') {
      throw new Error('Invalid token');
    }
      return jwtDecode(token);
      }
      catch(error){
        console.error('Token decoding failed',error);
        return null;
      }
      
  }


  getRoleFromToken(token:string):string | null {
    const decodedToken=this.decodeToken(token);
      if(decodedToken && decodedToken.role){
            return decodedToken.role;

      }
          return null;


  }
  setToken(token:string){
    localStorage.setItem('loginToken',token);
  }

  getTheToken(){
    return localStorage.getItem('loginToken')as string;
  }

  getToken(): string | null {
    return this.token || localStorage.getItem('loginToken');
  }
  

   /*decryptPassword(encryptedPassword:string, secretKey:string) {
    const bytes = CryptoJS.AES.decrypt(encryptedPassword, secretKey);
    try {
      return bytes.toString(CryptoJS.enc.Utf8);
    } catch (error) {
      console.error('Error decrypting password:', error);
      return 'Error decrypting password';
    }
    //const bytes = CryptoJS.AES.decrypt(encryptedPassword, secretKey);
    //return bytes.toString(CryptoJS.enc.Utf8);
   // return bytes.toString(CryptoJS.enc.Utf8);
}*/

logout(token:any){
  return this.http.post<any>("http://localhost:8082/hospital/auth/logout",token);
}


alertIncorrectAuthentication(username:string){

  return this.http.get("http://localhost:8082/hospital/auth/incorrectAuthentication/"+username)
}


}
