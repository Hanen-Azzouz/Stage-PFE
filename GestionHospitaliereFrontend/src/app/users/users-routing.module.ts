import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsersComponent } from './users.component';
import { AlltemplateBackComponent } from '../shared/components/alltemplate-back/alltemplate-back.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { LoginComponent } from '../shared/components/login/login.component';
import { AllUsersComponent } from './all-users/all-users.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { AddRoleComponent } from './add-role/add-role.component';
import { AllRolesComponent } from './all-roles/all-roles.component';
import { UpdateRoleComponent } from './update-role/update-role.component';

//const routes: Routes = [{ path: '', component: UsersComponent }];//path: '', component: UsersComponent
const routes: Routes = [

{ path: '', component:AlltemplateBackComponent ,
  

 children:[
    {path: 'users', component: UsersComponent},
    {path: 'register', component:RegisterUserComponent},
    {path: 'allUsers', component:AllUsersComponent},
    {path: 'updateUser/:id',component:UpdateUserComponent},
    {path: 'addRole',component:AddRoleComponent},
    {path: 'allRoles',component:AllRolesComponent},
    {path:'updateRole/:id',component:UpdateRoleComponent},
    
 ]

   
  
  }






];





@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsersRoutingModule { }
