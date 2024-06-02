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
delete(id:any){

}











}
