import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { LoginComponent } from '../shared/components/login/login.component';
import { FormsModule } from '@angular/forms';
import {NgxPaginationModule } from 'ngx-pagination';
import { AllUsersComponent } from './all-users/all-users.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import { AddRoleComponent } from './add-role/add-role.component';
import { AllRolesComponent } from './all-roles/all-roles.component';
import { UpdateRoleComponent } from './update-role/update-role.component';
import { ChangePWDComponent } from './change-pwd/change-pwd.component';


@NgModule({
  declarations: [
    UsersComponent,
    RegisterUserComponent,
    LoginComponent,
    AllUsersComponent,
    UpdateUserComponent,
    AddRoleComponent,
    AllRolesComponent,
    UpdateRoleComponent,
    ChangePWDComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    FormsModule,
    NgxPaginationModule,
    NgbModule,
    
   
  ]
})
export class UsersModule { }
