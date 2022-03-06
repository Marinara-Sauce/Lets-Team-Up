package com.letsteamup.api.letsteamupapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letsteamup.api.letsteamupapi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DAO for the User JSON file
 */
@Component
public class UserDAOFile 
{
    private Map<String, User> users;

    private String filename;
    private ObjectMapper mapper;

    private static int nextId;

    public UserDAOFile(@Value("${user_list.file}") String filename, ObjectMapper mapper) throws IOException
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
        users = new TreeMap<>();
        nextId = 0;

        User[] usersArray = mapper.readValue(new File(filename), User[].class);

        for (User u : usersArray)
        {
            users.put(u.getName(), u);
            if (u.getId() > nextId)
                nextId = u.getId();
        }

        ++nextId;
        return true;
    }

    private boolean save() throws IOException
    {
        User[] users = getUsersArray();

        mapper.writeValue(new File(filename), users);

        return true;
    }

    // -- HTTP Functions for Controller -- //

    private User[] getUsersArray()
    {
        return getUsersArray(null);
    }

    public User[] getUsersArray(String containsName)
    {
        List<User> usersWithName = new ArrayList<>();

        for (User u : users.values())
        {
            if (containsName == null || u.getName().equals(containsName))
                usersWithName.add(u);
        }

        User[] usersArray = new User[usersWithName.size()];
        usersWithName.toArray(usersArray);
        return usersArray;
    }

    public User getUser(String name)
    {
        synchronized (users)
        {
            if (users.containsKey(name))
                return users.get(name);
            
            return null;
        }
    }

    public User createUser(User u) throws IOException
    {
        synchronized(users)
        {
            if (users.containsKey(u.getName()))
                return null;

            User newUser = new User(nextId(), u.getName(), u.getPasswordHash(), 
                                    u.getTwitter(), u.getLinkedin(), u.getGithub(), 
                                    u.getEmail(), u.isExposeEmail(), u.getSkills());

            users.put(newUser.getName(), newUser);
            save();
            return newUser;
        }
    }

    public User updateUser(User u) throws IOException
    {
        synchronized(users)
        {
            if (!users.containsKey(u.getName()))
                return null;

            users.put(u.getName(), u);
            save();
            return u;
        }
    }

}
