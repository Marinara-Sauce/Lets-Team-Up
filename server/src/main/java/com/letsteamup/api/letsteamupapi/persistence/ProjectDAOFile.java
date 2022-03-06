package com.letsteamup.api.letsteamupapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsteamup.api.letsteamupapi.model.Project;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectDAOFile 
{
    
    private Map<Integer, Project> projects;

    private ObjectMapper mapper;
    private String filename;

    private static int nextId;

    public ProjectDAOFile (@Value("${project_list.file}") String filename, ObjectMapper mapper) throws IOException
    {
        this.filename = filename;
        this.mapper = mapper;
        load();
    }

    private synchronized int nextId()
    {
        int id = nextId;
        ++nextId;
        return id;
    }

    // -- File operations -- //
    
    private boolean load() throws IOException
    {
        projects = new TreeMap<>();
        nextId = 0;

        Project[] projectsArray = mapper.readValue(new File(filename), Project[].class);

        for (Project u : projectsArray)
        {
            projects.put(u.getId(), u);
            if (u.getId() > nextId)
                nextId = u.getId();
        }

        ++nextId;
        return true;
    }

    private boolean save() throws IOException
    {
        Project[] users = getProjectsArray();

        mapper.writeValue(new File(filename), users);

        return true;
    }

    private Project[] getProjectsArray()
    {
        return getProjectsArray(null);
    }

    public Project[] getProjectsArray(String containsName)
    {
        List<Project> usersWithName = new ArrayList<>();

        for (Project u : projects.values())
        {
            if (containsName == null || u.getTitle().equals(containsName))
                usersWithName.add(u);
        }

        Project[] usersArray = new Project[usersWithName.size()];
        usersWithName.toArray(usersArray);
        return usersArray;
    }

    public Project getProject(int id)
    {
        synchronized(projects)
        {
            if (!projects.containsKey(id))
                return null;
            
            return projects.get(id);
        }
    }

    public Project[] findProject(String name)
    {
        synchronized(projects)
        {
            return getProjectsArray(name);
        }
    }

    public Project createProject(Project p) throws IOException
    {
        synchronized(projects)
        {
            Project newProject = new Project(nextId(), p.getTitle(), 
                                    p.getDescription(), p.getElevatorPitch(),
                                    p.getOwner(), p.getSkillsNeeded());
            
            projects.put(newProject.getId(), newProject);
            save();
            return newProject;
        }
    }

    public boolean deleteProject(int id) throws IOException
    {
        synchronized(projects)
        {
            if (!projects.containsKey(id))
                return false;
            
            projects.remove(id);
            save();
            return true;
        }
    }

    

}
