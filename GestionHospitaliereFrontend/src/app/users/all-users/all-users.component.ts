import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoleModel } from 'src/app/shared/models/roleModel';
import { UserModel } from 'src/app/shared/models/userModel';
import { UserService } from 'src/app/shared/services/user.service';

@Component({
  selector: 'app-all-users',
  templateUrl: './all-users.component.html',
  styleUrls: ['./all-users.component.css']
})
export class AllUsersComponent implements OnInit{
usersList!:UserModel[];
role:RoleModel=new RoleModel();
p: number = 1;
user:UserModel=new UserModel();
user1:UserModel=new UserModel();

selected!: string
usernameToSearch: string = '';

constructor(private userService:UserService,private router:Router,private cdr: ChangeDetectorRef){}



ngOnInit(): void {
  this.getAllUsers();
 
}

  
    getAllUsers(){ 
      
      const startTime = performance.now();
      this.userService.getAllUsers().subscribe(
      (data: UserModel[])=>{(this.usersList=data);
        const endTime = performance.now();
          console.log(`getAllUsers completed in ${endTime - startTime} ms`);
      }
      
      
        );
      

    }
      afficherOneUser(id:any){
      console.log(id);
      this.userService.getOneUserById(id).subscribe(

      (data1:UserModel)=>{this.user=data1;
        console.log(this.user);
        console.log("go to modal");
        
      }

      );
      const modelDiv=document.getElementById('exampleModal');
      if(modelDiv!=null){
        modelDiv.style.display='bloc';}
      //this.cdr.detectChanges(); // Ensure Angular updates the view
              
      }




      closeModal() {
        const modelDiv=document.getElementById('exampleModal');
    if(modelDiv!=null){
      modelDiv.style.display='none';}
      }


    update(id:any){
    this.router.navigate(['/users/updateUser/'+id])
    }


    lockUserAccount(username:string){
      
    this.userService.lockUserAccount(username).subscribe(
      (response)=>{
              alert(response.message);
              console.log(response.message);               

              this.router.navigate(['/users/allUsers']);
              window.location.reload();
      },
      error => {
        console.error('Error unlocking user:', error);
      }
    );
    }


    UnlockUserAccount(username:string){
     
      this.userService.unlockUserAccount(username).subscribe(

        (response)=>{
                 alert(response.message);
                 this.router.navigate(['/users/allUsers']);
                 window.location.reload();

        },
        error => {
          console.error('Error unlocking user:', error);
        }
      );

    }


    delete(id:any){
      this.userService.deleteUser(id).subscribe(
        (response)=>{alert(response.message);
          this.router.navigate(['/users/allUsers']);
          window.location.reload();

 },
 error => {
   console.error('Error unlocking user:', error);
 }
       
      );
    }


    change() {
      console.log(this.selected);
      //this.refresh();
         
    }
    searchActifsUsers(f:any){

      this.userService.getActifsUsers(f.status).subscribe(
        (data:UserModel[])=>{this.usersList=data;}
      );
    }
    searchUserByUsername(f:any){
      this.userService.getUserByUsername(f).subscribe(

        (data:UserModel)=>{
          this.usersList=[data];
          console.log("user is ",data);
        }
      );

    }
    onSearch(): void {
      this.searchUserByUsername(this.usernameToSearch);
    }


    getUserByDateInscriptionBetween(f:any){
      this.userService.getUserByDateInscriptionBetween(f.dateDebut,f.dateFin).subscribe(

        (data:UserModel[])=>{

          this.usersList=data;
          console.log(data);
        }



      );
    }






}
