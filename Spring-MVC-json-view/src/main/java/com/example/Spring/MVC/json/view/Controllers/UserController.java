package com.example.Spring.MVC.json.view.Controllers;

import com.example.Spring.MVC.json.view.Entities.User;
import com.example.Spring.MVC.json.view.Exceptions.UserNotFoundException;
import com.example.Spring.MVC.json.view.Views.Views;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    ArrayList<User>users;

    @Autowired
    public UserController(ArrayList<User> users) {
        this.users = users;
    }

    @JsonView(Views.UserSummary.class)
    @GetMapping("/getUsers")
    public List<User> getUsers() throws JsonProcessingException {
        return users;
    }

    @JsonView(Views.UserDetails.class)
    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable Long id) throws JsonProcessingException {
       return users.stream().filter((User user) -> user.getId()==id).findFirst().orElseThrow(()->new UserNotFoundException(
                "User with id "+id+ " does not exist"));
    }

    @PostMapping("/create")
    public User createUser(@RequestBody @Valid User user){
        users.add(user);
        return users.get(users.size()-1);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@RequestBody @Valid User user,@PathVariable Long id){
       int modified = users.indexOf(users.stream().filter((User u) -> u.getId()==id).findFirst().orElseThrow(()-> new UserNotFoundException("No user with this id exists")));
       users.set(modified,user);
       return users.get(modified);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteUser(@PathVariable Long id){
     return users.remove(users.stream().filter((User user) -> user.getId()==id).findFirst().orElseThrow(()->new UserNotFoundException(
                "User with id "+id+ "does not exist")));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> UserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
