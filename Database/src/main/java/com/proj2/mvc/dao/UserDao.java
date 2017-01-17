package com.proj2.mvc.dao;



import java.util.List;

import com.proj2.mvc.model.User;

public interface UserDao {
	User authenticate(User user);
	void updateUser(User user);
	User registerUser(User user);
	public List<User> getAllUsers(User user);
}
