import { Component, OnInit, Input } from '@angular/core';
import { Project } from 'src/app/interfaces/project';

@Component({
  selector: 'app-project-list',
  templateUrl: './project-list.component.html',
  styleUrls: ['./project-list.component.css']
})
export class ProjectListComponent implements OnInit {

  @Input() project!: Project;

  constructor() { }

  ngOnInit(): void {
  }

}
