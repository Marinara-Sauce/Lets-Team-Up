package com.letsteamup.api.letsteamupapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representation of Projects
 */
public class Project
{

    @JsonProperty int id; //Generated by DAO

    // -- Defined by user -- //
    @JsonProperty String title;
    @JsonProperty String description;
    @JsonProperty String elevatorPitch;
    @JsonProperty String skillsNeeded;

    // -- Defined on creation -- //
    @JsonProperty String owner;

    // -- Defined through updates -- //
    @JsonProperty String team;
    @JsonProperty String[] interested;

    public Project(@JsonProperty int id, @JsonProperty String title, @JsonProperty String description, @JsonProperty String elevatorPitch,
                    @JsonProperty String owner, @JsonProperty String skillsNeeded, @JsonProperty String[] interested)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.elevatorPitch = elevatorPitch;
        this.owner = owner;
        this.skillsNeeded = skillsNeeded;
        this.interested = interested;
    }

    public Project()
    {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getElevatorPitch() {
        return elevatorPitch;
    }

    public void setElevatorPitch(String elevatorPitch) {
        this.elevatorPitch = elevatorPitch;
    }

    public String getSkillsNeeded() {
        return skillsNeeded;
    }

    public void setSkillsNeeded(String skillsNeeded) {
        this.skillsNeeded = skillsNeeded;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String[] interested() {
        return interested;
    }

    public void addInterestedPerson(String name) {
        List<String> interestedAlready = new ArrayList<>();
        
        for (String s : interested)
            interestedAlready.add(s);

        interestedAlready.add(name);
        String[] interest = new String[interestedAlready.size()];
        interestedAlready.toArray(interest);
        interested = interest;
    }
}