package com.letsteamup.api.letsteamupapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Basic class used to observe login attempts
 */
public class LoginAttempt 
{
    @JsonProperty String name;
    @JsonProperty String password;

    public LoginAttempt(@JsonProperty String name, @JsonProperty String password)
    {
        this.name = name;
        this.password = password;
    }

    public LoginAttempt()
    {

    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }
}
