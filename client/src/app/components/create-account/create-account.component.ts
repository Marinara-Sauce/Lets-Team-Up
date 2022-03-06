import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { User } from 'src/app/interfaces/user';
import { UserAccountsService } from 'src/app/services/user-accounts.service';

@Component({
  selector: 'app-create-account',
  templateUrl: './create-account.component.html',
  styleUrls: ['./create-account.component.css']
})
export class CreateAccountComponent implements OnInit {

  username!: string;
  password!: string;
  confirmPassword!: string;

  @Output() eventEmitter: EventEmitter<User> = new EventEmitter();

  constructor(private userAccountService: UserAccountsService) { }

  ngOnInit(): void {
  }

  createAccount(): void {

    if (!this.username || !this.password || !this.confirmPassword)
    {
      alert("Please fill out all fields!");
      return;
    }

    if (this.password !== this.confirmPassword)
    {
      alert("Please confirm that both passwords match!");
      return;
    }

    const newAccount = {
      id: 0,
      name: this.username,
      passwordHash: this.password,
      skills: "",
      linkedin: "",
      twitter: "",
      github: "",
      email: "",
      exposeEmail: false
    }

    this.userAccountService.createAccount(newAccount).subscribe(response => {
      this.userAccountService.setUser(response);
    });
  }
}
