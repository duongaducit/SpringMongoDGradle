package com.asiantech.ducdh.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asiantech.ducdh.demo.entity.User;
import com.asiantech.ducdh.demo.service.UserService;


@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
    @RequestMapping("/list")
    public List<User> greeting() {
        return userService.getUser();
    }
    
    @RequestMapping(value = "/add")
    public User addUser(@RequestParam(value = "firstName")String firstName,
    		@RequestParam(value = "lastName")String lastName,
    		@RequestParam(value = "email")String email,
    		@RequestParam(value = "password")String password){
    	User user = new User(firstName, lastName, email, password);
    	return userService.save(user);
    }
    
    @RequestMapping("/search")
    public User search(@RequestParam(value = "email")String email){
    	return userService.getUserByEmail(email);
    }
    
    @RequestMapping("/delete")
    public List<User> delete(@RequestParam(value = "email")String email){
    	return userService.deleteUser(email);
    }
    
    @RequestMapping("/update")
    public User update(@RequestParam(value = "firstName")String firstName,
    		@RequestParam(value = "lastName")String lastName,
    		@RequestParam(value = "email")String email,
    		@RequestParam(value = "password")String password){
    	User user = new User(firstName, lastName, email, password);
    	return userService.updateUser(user);
    }
}
