import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route } from '@angular/router';
import { User } from 'src/app/interfaces/user';
import { UserAccountsService } from 'src/app/services/user-accounts.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  name!: string;
  user!: User;

  constructor(private userService: UserAccountsService, private route: ActivatedRoute) { 
    this.route.queryParams.subscribe(params => {
      if (params['name'] !== undefined && params['name'] !== null)
      {
        console.log(params['name']);
        this.userService.getUser(params['name']).subscribe((value) => this.user = value);
      }
    });
  }

  ngOnInit(): void {
    
  }

  getUser(name: string) {
    this.userService.getUser(name).subscribe((user) => {
      this.user = user;
      console.log(user);
    });
  }

}
