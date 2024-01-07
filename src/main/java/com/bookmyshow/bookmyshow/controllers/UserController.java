package com.bookmyshow.bookmyshow.controllers;

import com.bookmyshow.bookmyshow.dtos.SignUpRequestDto;
import com.bookmyshow.bookmyshow.dtos.SignUpResponseDto;
import com.bookmyshow.bookmyshow.models.ResponseStatus;
import com.bookmyshow.bookmyshow.models.User;
import com.bookmyshow.bookmyshow.repository.UserRepository;
import com.bookmyshow.bookmyshow.services.UserService;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class UserController {
    private UserService userService;
    private UserRepository userRepository;

    UserController(UserService userService,UserRepository userRepository){
        this.userService=userService;
        this.userRepository=userRepository;
    }
    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto){
            String email = signUpRequestDto.getEmail();
            String password = signUpRequestDto.getPassword();
            User responseUser = new User();
            SignUpResponseDto signUpResponseDto =new SignUpResponseDto();
            responseUser = userService.signUp(email,password);
            signUpResponseDto.setResponseStatus(ResponseStatus.SUCCESS);
            signUpResponseDto.setUserId(responseUser.getId());
            return signUpResponseDto;
    }
}
