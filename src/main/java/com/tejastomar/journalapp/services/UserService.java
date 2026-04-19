package com.tejastomar.journalapp.services;

import lombok.extern.slf4j.Slf4j;
import com.tejastomar.journalapp.entity.User;
import com.tejastomar.journalapp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserService {

    @Autowired
    private UserRepository UserRepository;

    private static final PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();

    public void saveUser(User user){
        UserRepository.save(user);
    }

    public boolean saveNewUser(User user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            UserRepository.save(user);
            return true;
        }catch(Exception e){
            log.error("error occurred for");
            log.warn("hahahahahahhahahaha");
            log.info("hahahahahahhahahaha");
            log.debug("hahahahahahhahahaha");
            log.trace("hahahahahahhahahaha");
            return false;
        }
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        UserRepository.save(user);
    }

    public List<User> getAll(){
        return UserRepository.findAll();
    }
    public Optional<User> findById(ObjectId myId){
        return UserRepository.findById(myId);
    }
    public void deleteById(ObjectId myId){
        UserRepository.deleteById(myId);
    }
    public User findByUserName(String username){
        return UserRepository.findByUserName(username);
    }
}

//controller--->service--->repository