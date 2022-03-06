import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { CreateAccountComponent } from './components/create-account/create-account.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { EditUserComponent } from './components/edit-user/edit-user.component';

import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginReminderComponent } from './components/login-reminder/login-reminder.component';
import { ProjectComponent } from './components/project/project.component';
import { CreateProjectComponent } from './components/create-project/create-project.component';
import { ProjectListComponent } from './components/project-list/project-list.component';
import { UserComponent } from './components/user/user.component';

const appRoutes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'createaccount', component: CreateAccountComponent},
  {path: 'editaccount', component: EditUserComponent},
  {path: 'project', component: ProjectComponent},
  {path: 'createproject', component: CreateProjectComponent},
  {path: 'user', component: UserComponent}
]

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    CreateAccountComponent,
    EditUserComponent,
    HomeComponent,
    LoginReminderComponent,
    ProjectComponent,
    CreateProjectComponent,
    ProjectListComponent,
    UserComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
