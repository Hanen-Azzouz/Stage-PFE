import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserModel } from 'src/app/shared/models/userModel';
import { AuthenticationService } from 'src/app/shared/services/authentication.service';
import { RoleService } from 'src/app/shared/services/role.service';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{

  //username:any;
  user:UserModel=new UserModel();
  result!:any;
  accessToken!:string;
  connectedUser!:UserModel;
  token:string | null = null;
  role: string | null = null;
  username!:string;

  errorMessage!: string;
  loginAttempts: number = 0;
  maxAttempts: number = 3;
  isPWDExpired:boolean=false;


  constructor(private userService:UserService,
    private router:Router,
    private roleService:RoleService,
    private authService:AuthenticationService){

  }
  ngOnInit(): void {
    
  }
   
     
      
    
  login(f:any){
    this.username=f.username;
    //this.pwd=f.password;
    this.user.username=f.username;
    this.user.password=f.password;
   

    
    console.log("user will be sent is" +JSON.stringify(this.user));
    
    this.userService.loginUser(this.user).subscribe(
      (data:any)=>{
        this.result=data;
        // Gérer la réussite de la connexion
        this.errorMessage = '';
        this.loginAttempts = 0;
        this.accessToken=(this.result.access_token)as string;

                
        console.log("access token with modification of code is : ",this.accessToken);
        this.authService.setToken(this.accessToken);
        localStorage.setItem('role',data.role);
        this.role=localStorage.getItem('role');
        console.log("role from storage is:",this.role);
        console.log("response is" +(this.result.message)as string);
      
        this.userService.getUserByUsername(f.username).subscribe(

          (data:UserModel)=>{
            this.connectedUser=data;
            localStorage.setItem("connected_User",this.connectedUser.firstName+' '+this.connectedUser.lastName);
    
            console.log('connected user is : ',this.connectedUser);
            window.location.reload();

            //Check if PWD is expired
     console.log("username is "+this.username);
     this.userService.getPWDExpired(this.username).subscribe(
       (data:boolean)=>{
 
         this.isPWDExpired=data;
         console.log("response is"+this.isPWDExpired);
         if(this.isPWDExpired==true){
          this.router.navigate(['/users/updatePWD']);
        }
       
        },
       (error) => {
         // Handle error if needed
         console.error('Error fetching password expiration status:', error);});
     
            //window.location.reload();
            
          });


        

       //this.token = this.authService.getTheToken();
       

       //this.role=this.authService.getRoleFromToken(this.accessToken);
            //console.log('Role from token:', this.role);

      if(this.role== 'ADMIN'){

        this.router.navigate(['/users/']);
       
      }
      if(this.role=='MEDECIN'){
        this.router.navigate(['/users/']);
      }
      if(this.role=='ADMISSION'){
        this.router.navigate(['/users/']);
      }
      if(this.role=='FACTURATION'){
        this.router.navigate(['/users/']);
      }
      if(this.role=='CAISSIER'){
        this.router.navigate(['/users/']);
      }
      if(this.role=='HONORAIRE'){
        this.router.navigate(['/users/']);
      }
      if(this.role=='INFIRMIER'){
        this.router.navigate(['/users/']);
      }

      
  


      },
      error => {
        this.loginAttempts++;
        if (this.loginAttempts >= this.maxAttempts) {
          this.errorMessage = 'Trop de tentatives de connexion infructueuses. Veuillez réessayer plus tard.';
          alert(this.errorMessage);

        this.authService.alertIncorrectAuthentication( this.username).subscribe(

          (data)=>{console.log('mail was sent ok');
            
          }
        );
        
        
        
        }else{
        this.errorMessage = 'Nom d\'utilisateur ou mot de passe incorrect.';

      }
     
    
    }
      
    );
    
    
    
  


     
  }

}
