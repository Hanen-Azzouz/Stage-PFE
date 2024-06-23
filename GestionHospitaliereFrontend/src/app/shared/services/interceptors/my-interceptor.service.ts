import { HttpErrorResponse, HttpEvent, HttpEventType, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, tap, throwError } from 'rxjs';
import { AuthenticationService } from '../authentication.service';
import { UserService } from '../user.service';


/*@Injectable({
  providedIn: 'root'
})*/
export class MyInterceptorService implements HttpInterceptor{ 
  constructor() { }

intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
 //debugger;
 /*
const token = localStorage.getItem('loginToken')?.replace(/"/g, '');  
  const authReq=req.clone({
  setHeaders:{
  Authorization:`Bearer ${token}`
  }

  });
  return next.handle(authReq);

  */

  const token = localStorage.getItem('loginToken')?.replace(/"/g, '');
  if (token != null && token != undefined) {
    const authReq = req.clone({
      setHeaders: {
        Authorization: `Bearer ${token}`,
      },
    });
    return next.handle(authReq)
    
    /*.pipe(
      catchError((error: HttpErrorResponse)=>{
        if(error.status===401){
            const isRefresh= confirm("Your session is expired do you want to continue?")
              if(isRefresh){

              this.userService.$refreshToken.next(true);
              }
        
          }
        return throwError(error)
      })
      );*/
  }
  return next.handle(req);

  }




}
