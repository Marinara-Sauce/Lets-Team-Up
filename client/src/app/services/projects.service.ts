import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../interfaces/project';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

@Injectable({
  providedIn: 'root'
})
export class ProjectsService {

  private url: string = "http://localhost:8080/project";

  constructor(private httpClient: HttpClient) { }

  getProject(id: number): Observable<Project> {
    const url = `${this.url}/${id}`;
    return this.httpClient.get<Project>(url);
  }

  createProject(project: Project): Observable<Project> {
    console.log(project);
    return this.httpClient.post<Project>(this.url, project, httpOptions);
  }

  getProjects(): Observable<Project[]> {
    return this.httpClient.get<Project[]>(this.url);
  }
}
