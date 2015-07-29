package com.asiantech.ducdh.demo.service;

import java.util.List;

import com.asiantech.ducdh.demo.entity.User;

public interface UserService {
	
	User save(User user);
	
	List<User> getUser();
	
	User getUserByEmail(String email);

	List<User> deleteUser(String email);

	User updateUser(User user);
}
