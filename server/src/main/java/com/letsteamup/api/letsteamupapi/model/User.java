package com.letsteamup.api.letsteamupapi.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.letsteamup.api.letsteamupapi.util.Skills;

/**
 * Class represents a user, storing skills, socials, and information
 */
public class User
{
    private final String STRING_FORMAT = "{id=%d, name=%s, skills=%s, twitter=%s, linkedin=%s, github=%s, email=%s, exposeEmail=%b}";

    @JsonProperty private int id; //User ID

    @JsonProperty private String name; //Username
    @JsonProperty private String passwordHash; //Password (as a hash)

    @JsonProperty private String skills; //User's skills

    @JsonProperty private String twitter; //User's twitter
    @JsonProperty private String linkedin; //User's linkedin
    @JsonProperty private String github; //User's github page
    @JsonProperty private String email; //User's email address

    @JsonProperty private boolean exposeEmail; //Whether it expose the email address

    public User(@JsonProperty int id, @JsonProperty String name, @JsonProperty String passwordHash,
                @JsonProperty String skills, @JsonProperty String linkedin, @JsonProperty String twitter,
                @JsonProperty String github, @JsonProperty boolean exposeEmail, @JsonProperty String email)
    {
        this.id = id;
        this.name = name;
        this.passwordHash = passwordHash;
        this.skills = skills;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.github = github;
        this.email = email;
        this.exposeEmail = exposeEmail;
    }

    public User()
    {
        
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSkills() {
        return skills;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public String getGithub() {
        return github;
    }

    public String getEmail() {
        return email;
    }

    public boolean isExposeEmail() {
        return exposeEmail;
    }

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, id, name, skills, twitter, linkedin, github, email, exposeEmail);
    }
}
