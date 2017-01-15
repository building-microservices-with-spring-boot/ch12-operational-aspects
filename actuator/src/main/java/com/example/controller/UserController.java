package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository repository;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long userId) {
        User user = repository.findOne(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable Long userId) {
        //do not handle exception to generate metrics
        repository.delete(userId);
        return ResponseEntity.noContent().build();
    }


    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody User user) {
        User result = repository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
