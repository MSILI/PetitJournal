package com.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Article;
import com.app.util.DBUtil;

public class ArticleDaoImpl implements ArticleDao {

	private Connection conn;

	public ArticleDaoImpl() {
		// appel de la connexion a la bdd
		conn = DBUtil.getConnection();
	}

	@Override
	public void ajouter(Article article) {
		String req = "insert into article (titre, text, date, idUser) values (?,?,CURDATE(),?)";
		ResultSet valeursAutoGenerees = null;
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, article.getTitre());
			preparedStatement.setString(2, article.getText());
			preparedStatement.setInt(3, article.getUser().getId());
			preparedStatement.executeUpdate();

			valeursAutoGenerees = preparedStatement.getGeneratedKeys();
			if (valeursAutoGenerees.next()) {
				article.setId(valeursAutoGenerees.getInt(1));
			}
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void supprimer(int id) {
		String req = "delete from article where id = ?";
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
	public void modifier(Article article) {
		String req = "update article set titre=?, text=? where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setString(1, article.getTitre());
			preparedStatement.setString(2, article.getText());
			preparedStatement.setInt(3, article.getId());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Article> listerTout() {
		List<Article> articles = new ArrayList<Article>();
		UserDao userDao = new UserDaoImpl();
		String req = "select id,titre,text,DATE_FORMAT(date,'%d/%m/%Y') as date,idUser from article ORDER BY date DESC";
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(req);
			while (resultSet.next()) {
				Article article = new Article();
				article.setId(resultSet.getInt("id"));
				article.setTitre(resultSet.getString("titre"));
				article.setText(resultSet.getString("text"));
				article.setDate(resultSet.getString("date"));
				article.setUser(userDao.getUser(resultSet.getInt("idUser")));
				articles.add(article);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

	@Override
	public Article getArticle(int id) {
		Article article = new Article();
		UserDao userDao = new UserDaoImpl();
		String req = "select id,titre,text,DATE_FORMAT(date,'%d/%m/%Y') as date,idUser from article where id=?";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				article.setId(resultSet.getInt("id"));
				article.setTitre(resultSet.getString("titre"));
				article.setText(resultSet.getString("text"));
				article.setDate(resultSet.getString("date"));
				article.setUser(userDao.getUser(resultSet.getInt("idUser")));
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return article;
	}

	@Override
	public List<Article> listerParUser(int idUser) {
		List<Article> articles = new ArrayList<Article>();
		UserDao userDao = new UserDaoImpl();
		String req = "select id,titre,text,DATE_FORMAT(date,'%d/%m/%Y') as date,idUser from article where idUser = ? ORDER BY date DESC";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(req);
			preparedStatement.setInt(1, idUser);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Article article = new Article();
				article.setId(resultSet.getInt("id"));
				article.setTitre(resultSet.getString("titre"));
				article.setText(resultSet.getString("text"));
				article.setDate(resultSet.getString("date"));
				article.setUser(userDao.getUser(resultSet.getInt("idUser")));
				articles.add(article);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return articles;
	}

}
