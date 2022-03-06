import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { User } from 'src/app/interfaces/user';
import { ProjectsService } from 'src/app/services/projects.service';
import { UserAccountsService } from 'src/app/services/user-accounts.service';

@Component({
  selector: 'app-create-project',
  templateUrl: './create-project.component.html',
  styleUrls: ['./create-project.component.css']
})
export class CreateProjectComponent implements OnInit {

  title!: string;
  description!: string;
  elevatorPitch!: string;
  skillsNeeded!: string;

  user!: User;

  subscription: Subscription = new Subscription;

  constructor(private projectService: ProjectsService, private router: Router, private userService: UserAccountsService) { 
    this.subscription = this.userService.onLogin().subscribe((value) => this.user = value);
  }

  ngOnInit(): void {
  }

  createProject() {

    if (!this.title || !this.description || !this.elevatorPitch || !this.skillsNeeded)
    {
      alert("Please fill out all fields!");
      return;  
    }

    const project = {
      id: 0,
      title: this.title,
      description: this.description,
      elevatorPitch: this.elevatorPitch,
      skillsNeeded: this.skillsNeeded,
      owner: this.user.name,
      team: ""
    }

    this.projectService.createProject(project).subscribe(project => {
      this.router.navigate(['/project'], { queryParams: { id: project.id }});
    });
  }

}
