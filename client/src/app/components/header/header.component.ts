import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/interfaces/user';
import { UserAccountsService } from 'src/app/services/user-accounts.service';
import { Subscriber, Subscription } from 'rxjs';
import { noUndefined } from '@angular/compiler/src/util';
import { HttpHeaderResponse, HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  currentUser!: User;
  
  username!: string;
  password!: string;

  subscription: Subscription;

  constructor(private userAccountService: UserAccountsService) { 
    this.subscription = this.userAccountService.onLogin().subscribe(
      value => this.currentUser = value
    );
  }

  ngOnInit(): void {
    var tempUser = JSON.parse(localStorage.getItem('users') || '[]');

    if (tempUser !== '[]')
    {
      this.username = tempUser.name;
      this.password = tempUser.passwordHash;

      
      this.login();
    }
  }

  login(): void {
    this.userAccountService.logIntoAccount(this.username, this.password).subscribe(response => {
      if (response !== undefined)
      {
        this.userAccountService.setUser(response);
      }
    });
  }

  logout(): void {
    this.userAccountService.logOut();
  }

}
