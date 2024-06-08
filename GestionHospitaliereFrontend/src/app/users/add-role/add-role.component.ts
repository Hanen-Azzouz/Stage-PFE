import { Component, OnInit } from '@angular/core';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { RoleService } from 'src/app/shared/services/role.service';

@Component({
  selector: 'app-add-role',
  templateUrl: './add-role.component.html',
  styleUrls: ['./add-role.component.css']
})
export class AddRoleComponent implements OnInit {

  role:RoleModel=new RoleModel();
constructor(private roleService:RoleService){}

ngOnInit(): void {
  
}
 
  addRole(data:any){
    this.roleService.addRole(data).subscribe(
      (data:RoleModel)=>{
        console.log(data);
        console.log("role added");
      }
    );
  }
}
