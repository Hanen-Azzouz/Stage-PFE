import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/shared/models/userModel';
import { UserService } from 'src/app/shared/services/user.service';
import { FormsModule } from '@angular/forms';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { RoleService } from 'src/app/shared/services/role.service';



@Component({
  selector: 'app-register-user',
  templateUrl: './register-user.component.html',
  styleUrls: ['./register-user.component.css']
})
export class RegisterUserComponent implements OnInit {
addedUser!:UserModel;
roleAffecte!:RoleModel;
roleid!:any;
user:UserModel=new UserModel();


constructor(private userService:UserService,private router:Router,private roleService:RoleService){

}
ngOnInit(): void {
  
}
getRole(){
  this.roleid=this.roleAffecte;
  console.log("Id of role from DB is "+this.roleid);
this.roleService.getRoleById(this.roleid).subscribe(
  (data:any) => {

    this.roleAffecte=data;
   
    //this.user.role=data;
    console.log("role from DB is "+JSON.stringify(data));
    this.roleAffecte=data;
    console.log("role saved to affect before is "+JSON.stringify(this.roleAffecte));

  }
  
);

}

addUser(f:any){
  
console.log("role saved to affect  is  "+JSON.stringify(this.roleAffecte));
this.user.role=this.roleAffecte;
//this.user.role=this.roleAffecte;
console.log("Role affected to user is "+JSON.stringify(this.user.role));

this.userService.registerUser(this.user).subscribe(
(data:UserModel)=>{this.addedUser=data;


  console.log("user added"+JSON.stringify(this.addedUser));
  
  this.router.navigate(['/users/allUsers/'])
}


);

}
}
