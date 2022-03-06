import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { User } from '../interfaces/user';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

const httpTextOption = {
  headers: new HttpHeaders({
    'Content-Type': 'text/plain'
  })
}

@Injectable({
  providedIn: 'root'
})
export class UserAccountsService {

  private url: string = "http://localhost:8080/user"

  private user!: User;
  private subject = new Subject<any>();

  constructor(private http: HttpClient) { }

  setUser(user: any): void {
    this.user = user;
    this.subject.next(this.user);
  }

  onLogin(): Observable<any> {
    return this.subject.asObservable();
  }

  createAccount(user: User): Observable<User> {
    return this.http.post<User>(this.url, user, httpOptions);
  }

  logIntoAccount(username: string, enteredPassword: string): Observable<User> {
    const url = `${this.url}/login`;

    const loginAttempt = {
      name: username,
      password: enteredPassword
    }

    console.log(loginAttempt);
    return this.http.post<User>(url, loginAttempt, httpOptions);
  }

  logOut(): void {
    this.setUser(null);
  }
}
