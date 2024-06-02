import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { UserModel } from 'src/app/shared/models/userModel';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { RoleService } from 'src/app/shared/services/role.service';
import { UserService } from 'src/app/shared/services/user.service';


@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css'],
  
  
})
export class UpdateUserComponent implements OnInit{
  ident!:any;
  roleAffecte!:RoleModel;
roleid!:any;
  user:UserModel=new UserModel();
  encryptedPassword: string = '...';

  constructor(private userService:UserService,
    private router:Router,
    private ar:ActivatedRoute,
    private roleService:RoleService,
  private authService:AuthenticationService){}
  

ngOnInit(): void {
  
  this.ident=this.ar.snapshot.params['id'];
  this.userService.getOneUserById(this.ident).subscribe(
    (data)=>{this.user=data;
/* à vérifier ce code
      this.encryptedPassword=this.authService.decryptData(this.user.password);
      console.log("password is ",this.user.password);*/
// Exemple d'utilisation
const encryptedPassword = this.user.password;
const secretKey = '3b764a14b47285436cf8ebf16c1738689db9b192b97562b40732789cdd6eb688';
const decryptedPassword = this.authService.decryptPassword(encryptedPassword, secretKey);
console.log('Decrypted Password:', decryptedPassword);
    });


    
  
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


update(data:UserModel){



  this.user.role=this.roleAffecte;

  console.log('user updated is',this.user);
  return this.userService.updateUser(data).subscribe(
    ()=>{

      console.log('user updated');
      this.router.navigate(['/users/allUsers']);
    }
  

  );
  
}


}
