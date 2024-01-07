package com.bookmyshow.bookmyshow.services;

import com.bookmyshow.bookmyshow.models.User;
import com.bookmyshow.bookmyshow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    UserRepository userRepository;
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            throw new Exception("user doesn't exist");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        User user = optionalUser.get();
        if(bCryptPasswordEncoder.matches(password,user.getPassword())){
            return user;
        }
        throw new RuntimeException("Incorrect  Password");
    }

    public User signUp(String email,String password){
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        User user= new User();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        try {
            if (optionalUser.isEmpty()) {
                user.setEmail(email);
                user.setPassword(bCryptPasswordEncoder.encode(password));
                User savedUser = userRepository.save(user);
            } else {
                user = login(email, password);
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }
        // save it in the database
        return user;
    }
}
