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

/**
 * DAO for the User JSON file
 */
public class UserDAO 
{
    private Map<Integer, User> users;

    private String filename;
    private ObjectMapper mapper;

    private static int nextId;

    public UserDAO(@Value("${user_list.file}") String filename, ObjectMapper mapper) throws IOException
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
            users.put(u.getId(), u);
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

    private User[] getUsersArray(String containsName)
    {
        if (containsName == null)
            return (User[]) users.values().toArray();

        List<User> usersWithName = new ArrayList<>();
        
        for (User u : (User[]) users.values().toArray())
        {
            if (u.getName().equals(containsName))
                usersWithName.add(u);
        }

        return (User[]) usersWithName.toArray();
    }

    public User getUser(int id)
    {
        synchronized (users)
        {
            if (users.containsKey(id))
                return users.get(id);
            
                return null;
        }
    }

    public User createUser(User u) throws IOException
    {
        synchronized(users)
        {
            User newUser = new User(nextId(), u.getName(), u.getPasswordHash(), 
                                    u.getTwitter(), u.getLinkedin(), u.getGithub(), 
                                    u.getEmail(), u.isExposeEmail(), u.getSkills());

            users.put(newUser.getId(), newUser);
            save();
            return newUser;
        }
    }

    public User updateUser(User u) throws IOException
    {
        synchronized(users)
        {
            if (!users.containsKey(u.getId()))
                return null;

            users.put(u.getId(), u);
            save();
            return u;
        }
    }

}
