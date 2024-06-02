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


 
}
