import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RoleModel } from '../models/roleModel';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  constructor(private http :HttpClient) { }

getRoleById(id:any){


  return this.http.get<RoleModel>("http://localhost:8082/hospital/role/getRoleById/"+id);
}

getAllRoles(){

  return this.http.get<RoleModel[]>("http://localhost:8082/hospital/role/getAllRoles");
}

addRole(role:RoleModel){
  return this.http.post<RoleModel>("http://localhost:8082/hospital/role/addRole",role);
}

updateRole(role:RoleModel){
  return this.http.put<RoleModel>("http://localhost:8082/hospital/role/updateRole",role);

}

deleteRole(id:any){
  return this.http.delete<RoleModel>("http://localhost:8082/hospital/role/deleteRoleById/"+id)
}
}
