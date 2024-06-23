import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar-back',
  templateUrl: './sidebar-back.component.html',
  styleUrls: ['./sidebar-back.component.css']
})
export class SidebarBackComponent implements OnInit {
  


   isCollapsedusers=true;
   isCollapsedmedecins=true;


  constructor(){

  }
  
  ngOnInit(): void {}
  hasRole(role:string): boolean {
    const currentRole=localStorage.getItem('role');
    return currentRole !=undefined && currentRole===role;
  }

  isAdmin():boolean{
    return this .hasRole('ADMIN');
  }

isAdmissions():boolean{
  return this.hasRole('ADMIN')|| this.hasRole('INFIRMIER');
}

  isMedecin(): boolean {
    return this.hasRole('MEDECIN');
  }
  isInfirmier(): boolean {
    return this.hasRole('INFIRMIER');
  }
  isCaissier(): boolean {
    return this.hasRole('CAISSIER');
  }
  isAdmission(): boolean {
    return this.hasRole('ADMISSION');
  }
  isFacturation(): boolean {
    return this.hasRole('FACTURATION');
  }
  isHonoraire(): boolean {
    return this.hasRole('HONORAIRE');
  }
 
}
