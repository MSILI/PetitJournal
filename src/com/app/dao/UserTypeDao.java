package com.app.dao;

import java.util.List;

import com.app.model.UserType;

public interface UserTypeDao {

	public void ajouter(UserType UserType);

	public void supprimer(int idUserType);

	public void modifier(UserType UserType);

	public List<UserType> listerTout();

	public UserType getUserType(int idUserType);

}
