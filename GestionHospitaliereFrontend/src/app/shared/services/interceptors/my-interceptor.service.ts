import {
  HttpEvent,
  HttpEventType,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { AuthenticationService } from '../authentication.service';

/*@Injectable({
  providedIn: 'root'
})*/
export class MyInterceptorService implements HttpInterceptor {
  constructor() {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    //debugger;
    /*An example of interceptor
  console.log('My interceptor called');
 const modifiedReq=req.clone({headers:req.headers.append('auth','abcxyz')})
  return next.handle(modifiedReq).pipe(tap((event)=>{
    if (event.type===HttpEventType.Response){
      console.log('Respose has arrived. Response Data: ');
      console.log(event.body);
    }


  }));*/

    const token = localStorage.getItem('loginToken')?.replace(/"/g, '');
    if (token != null && token != undefined) {
      const authReq = req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`,
        },
      });
      return next.handle(authReq);
    }
    return next.handle(req);
  }
}
