import { Component, OnInit } from '@angular/core';
import { NgbDropdownModule } from '@ng-bootstrap/ng-bootstrap';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-header-back',
  templateUrl: './header-back.component.html',
  styleUrls: ['./header-back.component.css']
})
export class HeaderBackComponent implements OnInit{

connectedUser:String| null = null;;

constructor(private authService:AuthenticationService,private router:Router){}

ngOnInit(): void {
  this.connectedUser=localStorage.getItem('connected_User');
  


}
logout(){
  let token=this.authService.getTheToken();
this.authService.logout(token).subscribe(
  (data:any)=>{console.log("Response is "+(data.message)as string);
    this.router.navigate(['/login/']);
    localStorage.clear();
  }
);

}
changePwd(){
 
   this.router.navigate(['/users/updatePWD']);
}



}
