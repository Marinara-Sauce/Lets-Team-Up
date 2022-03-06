package com.letsteamup.api.letsteamupapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.letsteamup.api.letsteamupapi.model.Project;
import com.letsteamup.api.letsteamupapi.persistence.ProjectDAOFile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("project")
public class ProjectController 
{
    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private ProjectDAOFile projectDao;

    public ProjectController(ProjectDAOFile projectDao)
    {
        this.projectDao = projectDao;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable int id)
    {
        LOG.info("GET /project/" + id);
        
        Project p = projectDao.getProject(id);

        return new ResponseEntity<Project>(p, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Project[]> getProjects()
    {
        LOG.info("GET /project");

        return new ResponseEntity<Project[]>(projectDao.getProjectsArray(null), HttpStatus.OK);
    }

    @PostMapping("/recommended")
    public ResponseEntity<Project[]> getRecommendedProjects(@RequestBody String skills)
    {
        LOG.info("POST /project/recommended " + skills);

        Project[] projects = projectDao.getProjectsArray(null);
        String[] skillList = skills.split(", ");

        List<Project> recommendedProjects = new ArrayList<>();

        for (Project p : projects)
        {
            String[] projectSkillList = p.getSkillsNeeded().split(", ");

            for (String s : projectSkillList)
            {
                for (String sp : skillList)
                {
                    if (s.equalsIgnoreCase(sp) && !recommendedProjects.contains(p))
                        recommendedProjects.add(p);
                    
                    break;
                }
            }
        }

        Project[] projectsList = new Project[recommendedProjects.size()];
        recommendedProjects.toArray(projectsList);
        return new ResponseEntity<Project[]>(projectsList, HttpStatus.OK);
    }


    @GetMapping("/search/{name}")
    public ResponseEntity<Project[]> searchProject(@PathVariable String name)
    {
        LOG.info("GET /project/search/" + name);

        Project[] p = projectDao.findProject(name);

        return new ResponseEntity<Project[]>(p, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Project> createProject(@RequestBody Project p)
    {
        LOG.info("POST /project");

        try {
            Project newProject = projectDao.createProject(p);
            if (newProject == null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            else return new ResponseEntity<Project>(newProject, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Project> deleteProject(@PathVariable int id)
    {
        LOG.info("DELETE /project/" + id);

        try {
            if (projectDao.deleteProject(id))
                return new ResponseEntity<>(HttpStatus.OK);

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
