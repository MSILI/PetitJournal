package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.app.model.UserType;
import com.app.util.DBUtil;

public class UserTypeDaoImpl implements UserTypeDao {

	private Connection conn;

	public UserTypeDaoImpl() {
		// TODO Auto-generated constructor stub
		conn = DBUtil.getConnection();
	}

	@Override
	public void ajouter(UserType UserType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void supprimer(int idUserType) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifier(UserType UserType) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<UserType> listerTout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserType getUserType(int idUserType) {
		UserType userType = new UserType();
		String req = "select * from userType where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, idUserType);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userType.setId(resultSet.getInt("id"));
				userType.setLibelle(resultSet.getString("libelle"));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userType;
	}

}
