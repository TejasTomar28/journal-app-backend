package com.tejastomar.journalapp.controller;

import com.tejastomar.journalapp.cache.AppCache;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name="Admin APIs")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private AppCache appCache;


    @Operation(
            summary = "Get All Users",
            description = "Returns all registered users. Accessible only by ADMIN."
    )
    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Operation(
            summary = "Create Admin User",
            description = "Creates a new user with ADMIN privileges."
    )
    @PostMapping("/create-admin-user")
    public void createUser(@RequestBody User user){
        userService.saveAdmin(user);
    }


    @Hidden
    @Operation(
            summary = "Refresh Application Cache",
            description = "Reloads application configuration cache from MongoDB."
    )
    @GetMapping("/clear-app-cache")
    public void clearAppCache(){
        appCache.init();
    }
}
