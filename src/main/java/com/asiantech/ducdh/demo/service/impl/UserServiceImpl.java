package com.asiantech.ducdh.demo.service.impl;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiantech.ducdh.demo.entity.User;
import com.asiantech.ducdh.demo.repository.UserRepository;
import com.asiantech.ducdh.demo.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRespository;
	
	@Override
	public User save(User user) {
		if (user == null) throw new IllegalAccessError("User not null");
		User us = userRespository.findByEmail(user.getEmail());
		if (us == null){
			userRespository.save(user);
			user.setPassword("");
		}
		return user;
	}

	@Override
	public List<User> getUser() {
		List<User> list = userRespository.findAll();
		list.forEach(us -> us.setPassword(""));
		return list;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userRespository.findByEmail(email);
		user.setPassword("");
		return user;
	}

	@Override
	public boolean deleteUser(String email) {
		User user = userRespository.findByEmail(email);
		if (user == null)
		return false;
		userRespository.delete(user);
		return true;
	}

	@Override
	public boolean updateUser(User user) {
		if (user == null) throw new IllegalAccessError("User not null");
		User us = userRespository.findByEmail(user.getEmail());
		if (us != null){
			userRespository.save(user);
			return true;
		}
		return false;
	}

	@Override
	public boolean checkUser(User user) {
		// TODO Auto-generated method stub
		return false;
	}

}
