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

}
