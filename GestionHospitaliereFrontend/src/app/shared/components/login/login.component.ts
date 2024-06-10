import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/shared/models/userModel';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { RoleService } from 'src/app/shared/services/role.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  //username:any;
  user: UserModel = new UserModel();
  result!: any;
  accessToken!: string;
  token: string | null = null;
  role: string | null = null;

  constructor(
    private userService: UserService,
    private router: Router,
    private roleService: RoleService,
    private authService: AuthenticationService
  ) {}
  ngOnInit(): void {}

  login(f: any) {
    //this.username=f.username;
    //this.pwd=f.password;
    this.user.username = f.username;
    this.user.password = f.password;

    console.log('user will be sent is' + JSON.stringify(this.user));

    this.userService.loginUser(this.user).subscribe((data: any) => {
      this.result = data;
      this.accessToken = JSON.stringify(this.result.access_token);
      console.log('access token is : ', this.accessToken);
      localStorage.setItem('loginToken', this.accessToken.replace(/"/g, ''));
      localStorage.setItem('role', data.role);

      console.log('response is' + JSON.stringify(this.result.message));
      this.token = localStorage.getItem('loginToken');

      this.role = this.authService.getRoleFromToken(this.accessToken);
      console.log('Role from token:', this.role);

      if (this.role == 'ADMIN') {
        this.router.navigate(['/users/']);
      }
      if (this.role == 'MEDECIN') {
        this.router.navigate(['/users/']);
      }
    });
  }
}
