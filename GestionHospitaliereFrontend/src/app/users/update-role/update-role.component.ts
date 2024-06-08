import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { RoleService } from 'src/app/shared/services/role.service';

@Component({
  selector: 'app-update-role',
  templateUrl: './update-role.component.html',
  styleUrls: ['./update-role.component.css']
})
export class UpdateRoleComponent implements OnInit{

  ident!:any;
  role:RoleModel=new RoleModel();



  constructor(private roleService:RoleService, private router:Router, private ar:ActivatedRoute){}


  ngOnInit(): void {

/*this.ident=this.ar.snapshot.params['id'];
this.userService.getOneUserById(this.ident).subscribe(
  (data)=>{this.user=data;*/
  this.ident=this.ar.snapshot.params['id'];
  this.roleService.getRoleById(this.ident).subscribe(
    (data:RoleModel)=>{this.role=data;
      console.log("role to update is:",this.role);
    }
  );

  }




  updateRole(f1:RoleModel){
    this.roleService.updateRole(f1).subscribe(
      (data:RoleModel)=>{
        
        this.role=data;
        console.log(data);
      }
    );
  }
}
