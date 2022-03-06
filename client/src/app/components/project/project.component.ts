import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { Project } from 'src/app/interfaces/project';
import { User } from 'src/app/interfaces/user';
import { ProjectsService } from 'src/app/services/projects.service';
import { UserAccountsService } from 'src/app/services/user-accounts.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  project!: Project;
  currentUser!: User;

  interested!: String[];

  subscription!: Subscription;

  constructor(private route: ActivatedRoute, private projectService: ProjectsService, private userAccountService: UserAccountsService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['id'] !== undefined || params['id'] !== null)
      {
        this.projectService.getProject(params['id']).subscribe((value) => this.project = value);
      }
    });

    this.subscription = this.userAccountService.onLogin().subscribe(
      value => this.currentUser = value
    );
  }

  addInterested() {
    this.projectService.addInterested(this.currentUser.name, this.project).subscribe((value) => this.project = value);
  }

}
