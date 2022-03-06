import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Project } from '../interfaces/project';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json'
  })
}

const httpOptionsText = {
  headers: new HttpHeaders({
    'Content-Type': 'text/plain'
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

  getProjects(skills: String): Observable<Project[]> {
    const url = `${this.url}/recommended`;
    return this.httpClient.post<Project[]>(url, skills, httpOptionsText);
  }

  addInterested(name: String, project: Project): Observable<Project> {

    const interested = {
      name: name,
      project: project
    }

    return this.httpClient.put<Project>(this.url, interested, httpOptions);

  } 
}
