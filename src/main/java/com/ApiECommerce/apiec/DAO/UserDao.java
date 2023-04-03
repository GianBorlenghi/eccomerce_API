package com.ApiECommerce.apiec.DAO;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ApiECommerce.apiec.Exception.UserNotFoundException;
import com.ApiECommerce.apiec.Model.*;
import com.ApiECommerce.apiec.Repository.*;

@Service
public class UserDao implements IUserDao{

	@Autowired
	private IUserRepository userRepository;

	
	@Override
	@Transactional(readOnly=true)
	public User findByUsername(String username) {

		User user = userRepository.findByUsernameUser(username);
	
		return user;
	}


	@Override
	@Transactional
	public void save(User user) {

		userRepository.save(user);
		
	}


	@Override
	@Transactional(readOnly=true)
	public int findUserByMail(String mail) {
		return userRepository.findByMail(mail);
	}


	@Override
	@Transactional
	public void deleteUserById(Long id) {
		
		userRepository.deleteById(id);
		
	}


	@Override
	@Transactional(readOnly=true)
	public User findUserById(Long id) {
		return userRepository.findById(id).orElseThrow();
	}


	@Override
	@Transactional(readOnly=true)
	public List<String> viewConnectedUsers() {
		
		return userRepository.viewConnectedUsers();
	}


	@Override
	public void defaultAdmin(User administrator) {
	
		
		
	}



	


	
}