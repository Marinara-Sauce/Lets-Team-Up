import { HttpClientModule } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Project } from 'src/app/interfaces/project';
import { ProjectsService } from 'src/app/services/projects.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.css']
})
export class ProjectComponent implements OnInit {

  project!: Project;

  constructor(private route: ActivatedRoute, private projectService: ProjectsService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params['id'] !== undefined || params['id'] !== null)
      {
        this.projectService.getProject(params['id']).subscribe((value) => this.project = value);
      }
    })
  }

}
