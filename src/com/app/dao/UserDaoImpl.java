package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.model.User;
import com.app.util.DBUtil;

public class UserDaoImpl implements UserDao {

	private Connection conn;

	public UserDaoImpl() {
		// TODO Auto-generated constructor stub
		conn = DBUtil.getConnection();
	}

	@Override
	public void ajouter(User user) {
		String req = "insert into user (nom, prenom, login, password, idUserType) values (?,?,?,?,?)";
		ResultSet valeursAutoGenerees = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, user.getNom());
			preparedStatement.setString(2, user.getPrenom());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setInt(5, user.getUserType().getId());
			preparedStatement.executeUpdate();

			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				user.setId(valeursAutoGenerees.getInt(1));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void supprimer(int idUser) {
		String req = "delete from user where id = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, idUser);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void modifier(User user) {
		String req = "update user set nom=?, prenom=?, login=?, password=?, idUserType = ? where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setString(1, user.getNom());
			preparedStatement.setString(2, user.getPrenom());
			preparedStatement.setString(3, user.getLogin());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setInt(5, user.getUserType().getId());
			preparedStatement.setInt(6, user.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<User> listerTout() {
		List<User> users = new ArrayList<User>();
		UserTypeDao userTypeDao = new UserTypeDaoImpl();
		String req = "select * from user ORDER BY id";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(req);
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType(userTypeDao.getUserType(resultSet.getInt("idUserType")));
				users.add(user);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public User getUser(int idUser) {
		User user = new User();
		UserTypeDao userTypeDao = new UserTypeDaoImpl();
		String req = "select * from user where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, idUser);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType(userTypeDao.getUserType(resultSet.getInt("idUserType")));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserWithLoginAndpassword(String login, String password) {
		User user = null;
		UserTypeDao userTypeDao = new UserTypeDaoImpl();
		String req = "select * from user where login=? and password =?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setString(1, login);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user = new User();
				user.setId(resultSet.getInt("id"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setLogin(resultSet.getString("login"));
				user.setPassword(resultSet.getString("password"));
				user.setUserType(userTypeDao.getUserType(resultSet.getInt("idUserType")));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

}
