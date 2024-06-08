import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { RoleService } from 'src/app/shared/services/role.service';

@Component({
  selector: 'app-all-roles',
  templateUrl: './all-roles.component.html',
  styleUrls: ['./all-roles.component.css']
})
export class AllRolesComponent  implements OnInit{
  p: number = 1;
rolesList!:RoleModel[];

  constructor(private roleService:RoleService, private router:Router){}

  ngOnInit(): void {

    this.roleService.getAllRoles().subscribe(
      (data:RoleModel[])=>{
        this.rolesList=data;
        console.log("Roles are :",this.rolesList);
      }


    );
  }




  update(id:number){
    this.router.navigate(['/users/updateRole/'+id])

  }
  delete(id:number){
    this.roleService.deleteRole(id).subscribe(
      (sata)=>{console.log("role deleted")
        this.router.navigate(['/users/allRoles/']);
        window.location.reload();
      }
    );
  }
}
