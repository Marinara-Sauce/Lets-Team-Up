package com.letsteamup.api.letsteamupapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import com.letsteamup.api.letsteamupapi.model.User;
import com.letsteamup.api.letsteamupapi.persistence.UserDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP Controller for users
 */
@RestController
@RequestMapping("/user")
public class UserController 
{

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private UserDAO userDao;

    public UserController(UserDAO userDao)
    {
        this.userDao = userDao;
    }

    /**
     * Fetches any user with a specific username
     * 
     * @param username the username
     * @return OK if the user is found and the user
     */
    @GetMapping("/name={name}")
    public ResponseEntity<User[]> getUser(@RequestParam String username)
    {
        LOG.info("GET /user/name=" + username);

        User[] users = userDao.getUsersArray(username);

        return new ResponseEntity<User[]>(users, HttpStatus.OK);
    }

    /**
     * Fetches a user with an ID
     * 
     * @param id the id
     * @return OK if the user is found, NOT_FOUND if they don't exist
     */
    @GetMapping("/id={}")
    public ResponseEntity<User> getUser(@RequestParam int id)
    {
        LOG.info("GET /user/id=" + id);

        User user = userDao.getUser(id);

        if (user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Creates a new user
     * 
     * @param u the user to create
     * @return CREATED if the user was made
     * @return CONFLICT if the user is conflicting
     */
    @PostMapping("")
    public ResponseEntity<User> createUser(@RequestBody User u)
    {
        LOG.info("POST /user");

        try {
            User newUser = userDao.createUser(u);

            if (newUser == null)
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            else
                return new ResponseEntity<User>(u, HttpStatus.CREATED);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates a user
     * 
     * @param u the user to update
     * @return OK if the user was updated
     * @return NOT_FOUND if the user wasn't found
     */
    @PutMapping("")
    public ResponseEntity<User> updateUser(@RequestBody User u)
    {
        LOG.info("PUT /user");

        try {
            User newUser = userDao.updateUser(u);

            if (newUser == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            else
                return new ResponseEntity<User>(u, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}