package com.asiantech.ducdh.demo.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.asiantech.ducdh.demo.entity.User;
import com.asiantech.ducdh.demo.repository.UserRepository;
import com.asiantech.ducdh.demo.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Mock
	private UserRepository userRepository;
	
	private User userOutput;
	private List<User> listOutput;
	
	private List<User> createListUser(int length) {
		List<User> quesMap = new ArrayList<>();
		for (int i = 0; i < length; i++) {
			User user = createUser(i);
			quesMap.add(user);
		}
		return quesMap;
	}

	private User createUser(int i) {
		User user = new User("fn_" + i, "ls_" + i, "email_" + i, "ps_" + i);
		return user;
	}
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		userOutput = createUser(1);
		listOutput = createListUser(10);
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(userOutput);
		
		Mockito.when(userRepository.findAll()).thenReturn(listOutput);
		
		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(userOutput);
		
		Mockito.stubVoid(userRepository).toReturn().on().delete(Mockito.any(User.class));
	}

	@Test
	public void testSave() {
		User user = createUser(1);
		user = userService.save(user);
		Assert.assertNotNull(user);
	}

	@Test
	public void testGetUser() {
		List<User> list = userService.getUser();
		Assert.assertEquals(list.size(), 10);
	}

	@Test
	public void testGetUserByEmail() {
		User user = userService.getUserByEmail("email_2");
		Assert.assertNotNull(user);
	}

	@Test
	public void testDeleteUser() {
		userService.deleteUser("email_1");
		System.out.println("Finished the delete, no exception thrown");

	}

	@Test
	public void testUpdateUser() {
		User user = createUser(1);
		Assert.assertTrue(userService.updateUser(user));
	}

}
