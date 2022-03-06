import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Project } from 'src/app/interfaces/project';
import { User } from 'src/app/interfaces/user';
import { ProjectsService } from 'src/app/services/projects.service';
import { UserAccountsService } from 'src/app/services/user-accounts.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  user!: User;
  subscription: Subscription = new Subscription;

  projects!: Project[];

  constructor(private projectService: ProjectsService, private userService: UserAccountsService) { 
    this.subscription = this.userService.onLogin().subscribe((value) => {
      this.user = value
      this.projectService.getProjects(this.user.skills).subscribe((value) => this.projects = value);
    });
  }

  ngOnInit(): void {

  }

}
