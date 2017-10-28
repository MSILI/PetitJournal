package com.app.dao;

import java.util.List;

import com.app.model.Article;

public interface ArticleDao {
	
	public void ajouter(Article article);

	public void supprimer(int id);

	public void modifier(Article article);

	public List<Article> listerTout();
	
	public List<Article> listerParUser(int idUser);

	public Article getArticle(int id);

}
