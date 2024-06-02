import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UsersRoutingModule } from './users-routing.module';
import { UsersComponent } from './users.component';
import { RegisterUserComponent } from './register-user/register-user.component';
import { LoginComponent } from '../shared/components/login/login.component';
import { FormsModule } from '@angular/forms';
import {NgxPaginationModule } from 'ngx-pagination';
import { AllUsersComponent } from './all-users/all-users.component';
import { UpdateUserComponent } from './update-user/update-user.component';


@NgModule({
  declarations: [
    UsersComponent,
    RegisterUserComponent,
    LoginComponent,
    AllUsersComponent,
    UpdateUserComponent
  ],
  imports: [
    CommonModule,
    UsersRoutingModule,
    FormsModule,
    NgxPaginationModule,
   
  ]
})
export class UsersModule { }
