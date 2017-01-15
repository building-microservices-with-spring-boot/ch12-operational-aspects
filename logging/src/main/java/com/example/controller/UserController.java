package com.example.controller;

import com.example.model.User;
import com.example.repository.UserRepository;
import com.example.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository repository;

    @Autowired
    LocationService locationService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity get(@PathVariable Long userId) {
        LOGGER.debug("Requested user with id {}", userId);
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
        locationService.checkLocation(user.getLocation());
        User result = repository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{userId}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }
}
