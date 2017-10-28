package com.app.dao;

import java.util.List;

import com.app.model.User;

public interface UserDao {

	public void ajouter(User user);

	public void supprimer(int idUser);

	public void modifier(User user);

	public List<User> listerTout();

	public User getUser(int idUser);
	
	public User getUserWithLoginAndpassword(String login,String password);
}
