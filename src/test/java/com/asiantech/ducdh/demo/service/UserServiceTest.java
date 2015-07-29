package com.asiantech.ducdh.demo.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.asiantech.ducdh.demo.entity.User;
import com.asiantech.ducdh.demo.repository.UserRepository;
import com.asiantech.ducdh.demo.service.impl.UserServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
	
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	
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
	
	@Spy
	private List<User> listMongo = createListUser(10);
	
	@Mock
	private UserRepository userDAO;
	
	
	@Before
	public void setup(){
		//userDAO save
		Mockito.when(userDAO.save(Mockito.any(User.class))).then(new Answer<User>() {
			public User answer(InvocationOnMock invocation)
					throws Throwable {

				User user = (User) invocation.getArguments()[0];
				listMongo.add(user);
				return user;
				}
		});
		
		//userDao delete
		Mockito.doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) throws Throwable {
				User user = (User) invocation.getArguments()[0];
				listMongo.remove(user);
				return null;
			}
		}).when(userDAO).delete(Mockito.any(User.class));
		//userDAO getUser
		
		Mockito.when(userDAO.findAll()).then(new Answer<List<User>>() {
			@Override
			public List<User> answer(InvocationOnMock invocation) throws Throwable {
				return listMongo;
			}
		});
		
		//userDAO getUserByEmail
		Mockito.when(userDAO.findByEmail(Mockito.anyString())).then(new Answer<User>(){

			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				String email = (String) invocation.getArguments()[0];
				Optional<User> user = listMongo.stream().filter(us -> email.equals(us.getEmail())).findFirst();
				if (user.isPresent()){
					User rs = user.get();
				return rs;
				}
				else{
					return null;
				}
			}
			
		});
	}

	@Test
	public void testSave() {
		User user = createUser(12);
		user = userServiceImpl.save(user);
		assertNotNull(user);
		Assert.assertTrue("".equals(user.getPassword()));
	}

	@Test
	public void testGetUser() {
		List<User> list = userServiceImpl.getUser();
		Assert.assertTrue(check(list) && list.size() == 10);
	}

	@Test
	public void testGetUserByEmail() {
		User user = userServiceImpl.getUserByEmail("email_1");
		Assert.assertTrue((user != null) && "".equals(user.getPassword()));
	}
	
	@Test
	public void testDeleteUser(){
		List<User> list = userServiceImpl.deleteUser("email_1");
		Assert.assertTrue(check(list) && list.size() == 9);
	}
	
	@Test
	public void testUpdateUser(){
		User user = new User("fn_1", "ls_2", "email_3", "password_4");
		user = userServiceImpl.updateUser(user);
		assertNotNull(user);
		Assert.assertTrue("ls_2".equals(userServiceImpl.getUserByEmail("email_3").getLastName()));
		Assert.assertTrue(listMongo.size() == 10);
	}
	
	boolean check(List<User> list){
		for (User user:list){
			if (!"".equals(user.getPassword())){
				return false;
			}
		}
		return true;
	}

}
