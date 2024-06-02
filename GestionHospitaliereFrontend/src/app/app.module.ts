import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from '@angular/forms';
import { HttpClientModule,HTTP_INTERCEPTORS } from '@angular/common/http';
import { AlltemplateBackComponent } from './shared/components/alltemplate-back/alltemplate-back.component';
import { HeaderBackComponent } from './shared/components/header-back/header-back.component';
import { FooterBackComponent } from './shared/components/footer-back/footer-back.component';
import { SidebarBackComponent } from './shared/components/sidebar-back/sidebar-back.component';
import { MyInterceptorService } from './shared/services/interceptors/my-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    AlltemplateBackComponent,
    HeaderBackComponent,
    FooterBackComponent,
    SidebarBackComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
    MatSlideToggleModule,
  ],
  providers: [{provide: HTTP_INTERCEPTORS,useClass:MyInterceptorService,multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
