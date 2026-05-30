package com.tejastomar.journalapp.controller;

import com.tejastomar.journalapp.api.response.WeatherResponse;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.repository.UserRepository;
import com.tejastomar.journalapp.services.UserService;
import com.tejastomar.journalapp.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")//puri class pe mapping kar dega
@Tag(name = "User APIs", description = "Read, Update & Delete User")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository UserRepository;

    @Autowired
    private WeatherService weatherService;


    @Operation(
            summary = "Update User Profile",
            description = "Updates username and password of the authenticated user."
    )
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User userInDb = userService.findByUserName(username);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Delete User Account",
            description = "Deletes the currently authenticated user account."
    )
    @DeleteMapping
    public ResponseEntity<?> deleteUserById(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Operation(
            summary = "Get Personalized Greeting",
            description = "Returns a greeting message along with current weather information."
    )
    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse= weatherService.getWeather("Delhi");
        String greeting="";
        if(weatherResponse != null){
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelsLike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting ,HttpStatus.OK);
    }
}
