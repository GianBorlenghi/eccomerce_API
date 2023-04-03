package com.ApiECommerce.apiec.DAO;
import java.util.List;

import com.ApiECommerce.apiec.Model.User;


public interface IUserDao {
	
	public User findByUsername(String username);
	
	public void save(User user);
	
	public int findUserByMail(String mail);
	
	public void deleteUserById(Long id);
	
	public User findUserById(Long id);
	
	public List<String> viewConnectedUsers();
	
	public void defaultAdmin(User administrator);
	
}