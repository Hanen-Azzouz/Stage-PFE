import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-change-pwd',
  templateUrl: './change-pwd.component.html',
  styleUrls: ['./change-pwd.component.css']
})
export class ChangePWDComponent implements OnInit{

  
constructor(private userService:UserService,private router:Router){}



ngOnInit(): void {  
}




onSubmit(f:any) {
console.log("verification","new"+f.newPassword+"confirmation"+f.confirmationPassword+"current"+f.currentPassword)
    if (f.newPassword !== f.confirmationPassword) {
      alert('New Password and Confirm Password do not match');
      return;
    }
  
this.userService.changePwd(f.currentPassword,f.newPassword,f.confirmationPassword).subscribe(

  (data)=>{

    console.log("pwd changed ok");
    this.router.navigate(['/users/']);
  }
);

}

}
