package com.letsteamup.api.letsteamupapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Interested 
{

    @JsonProperty String name;
    @JsonProperty Project project;

    public Interested(@JsonProperty String name, @JsonProperty Project project)
    {
        this.name = name;
        this.project = project;
    }

    public Interested()
    {

    }

    public String getName()
    {
        return name;
    }

    public Project getProject()
    {
        return project;
    }
    
}
