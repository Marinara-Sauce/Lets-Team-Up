package com.letsteamup.api.letsteamupapi.controller;

import java.io.IOException;
import java.util.logging.Logger;

import com.letsteamup.api.letsteamupapi.model.LoginAttempt;
import com.letsteamup.api.letsteamupapi.model.User;
import com.letsteamup.api.letsteamupapi.persistence.UserDAOFile;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HTTP Controller for users
 */
@RestController
@RequestMapping("user")
public class UserController 
{

    private static final Logger LOG = Logger.getLogger(UserController.class.getName());

    private UserDAOFile userDao;

    public UserController(UserDAOFile userDao)
    {
        this.userDao = userDao;
    }

    /**
     * Fetches any user with a specific username
     * 
     * @param username the username
     * @return OK if the user is found and the user
     */
    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username)
    {
        LOG.info("GET /user/" + username);

        User users = userDao.getUser(username);

        return new ResponseEntity<User>(users, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginAttempt attempt)
    {
        LOG.info("POST /user/login");

        User[] user = userDao.getUsersArray(attempt.getName());

        if (user.length == 0)
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        if (user[0].getPasswordHash().equals(attempt.getPassword()))
            return new ResponseEntity<User>(user[0], HttpStatus.OK);
        
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("")
    public ResponseEntity<User[]> getUsers()
    {
        LOG.info("GET /user");

        User[] users = userDao.getUsersArray(null);

        return new ResponseEntity<User[]>(users, HttpStatus.OK);
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
