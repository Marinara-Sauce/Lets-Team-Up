import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { UserAccountsService } from 'src/app/services/user-accounts.service';
import { User } from '../../interfaces/user';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  linkedIn!: string;
  twitter!: string;
  github!: string;
  skills!: string;
  email!: string;
  username!: string;

  subscription!: Subscription;
  currentUser!: User;

  constructor(private userAccountService: UserAccountsService) { 
    this.subscription = this.userAccountService.onLogin().subscribe((value) => this.currentUser = value);
  }

  ngOnInit(): void {

  }

  editAccount() {

    //Check for empty strings, replace them with the current values
    if (!this.twitter)
      this.twitter = this.currentUser.twitter;
    
    if (!this.linkedIn)
      this.linkedIn = this.currentUser.linkedin;
    
    if (!this.skills)
      this.skills = this.currentUser.skills;
    
    if (!this.email)
      this.email = this.currentUser.email;
    
    if (!this.github)
      this.github = this.currentUser.github;

    const newUser = {
      id: this.currentUser.id,
      name: this.currentUser.name,
      passwordHash: this.currentUser.passwordHash,
      github: this.github,
      skills: this.skills,
      email: this.email,
      twitter: this.twitter,
      linkedin: this.linkedIn,
      exposeEmail: true
    }

    this.userAccountService.editAccount(newUser).subscribe((value) => {
      if (value)
      {
        this.currentUser = value;
        this.userAccountService.setUser(value);
      }
    });
  }

}
