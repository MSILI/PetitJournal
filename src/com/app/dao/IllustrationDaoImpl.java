package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Illustration;
import com.app.util.DBUtil;

public class IllustrationDaoImpl implements IllustrationDao {

	private Connection conn;

	public IllustrationDaoImpl() {
		// TODO Auto-generated constructor stub
		conn = DBUtil.getConnection();
	}

	@Override
	public void ajouter(Illustration illustration) {
		String req = "insert into illustration (titre, image, auteur, idArticle) values (?,?,?,?)";
		ResultSet valeursAutoGenerees = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, illustration.getTitre());
			preparedStatement.setBinaryStream(2, illustration.getImage());
			preparedStatement.setInt(3, illustration.getUser().getId());
			preparedStatement.setInt(4, illustration.getArticle().getId());
			preparedStatement.executeUpdate();
			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				illustration.setId(valeursAutoGenerees.getInt(1));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void supprimer(int id) {
		String req = "delete from illustration where id = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void modifier(Illustration illustration) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Illustration> listerTout() {
		List<Illustration> illustrations = new ArrayList<Illustration>();
		UserDao userDao = new UserDaoImpl();
		String req = "select * from illustration";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Illustration illustration = new Illustration();
				illustration.setId(resultSet.getInt("id"));
				illustration.setTitre(resultSet.getString("titre"));
				illustration.setUser(userDao.getUser(resultSet.getInt("auteur")));
				illustration.setImage(resultSet.getBinaryStream("image"));
				illustrations.add(illustration);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return illustrations;
	}

	@Override
	public Illustration getIllustration(int id) {
		Illustration illustration = new Illustration();
		ArticleDao articleDao = new ArticleDaoImpl();
		UserDao userDao = new UserDaoImpl();
		String req = "select * from illustration where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				illustration.setId(resultSet.getInt("id"));
				illustration.setUser(userDao.getUser(resultSet.getInt("auteur")));
				illustration.setImage(resultSet.getBinaryStream("image"));
				illustration.setTitre(resultSet.getString("titre"));
				illustration.setArticle(articleDao.getArticle(resultSet.getInt("idArticle")));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return illustration;
	}

	@Override
	public List<Illustration> listerParIdArt(int idArt) {
		List<Illustration> illustrations = new ArrayList<Illustration>();
		UserDao userDao = new UserDaoImpl();
		String req = "select * from illustration where idArticle = ?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, idArt);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Illustration illustration = new Illustration();
				illustration.setId(resultSet.getInt("id"));
				illustration.setTitre(resultSet.getString("titre"));
				illustration.setUser(userDao.getUser(resultSet.getInt("auteur")));
				illustration.setImage(resultSet.getBinaryStream("image"));
				illustrations.add(illustration);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return illustrations;
	}

}
