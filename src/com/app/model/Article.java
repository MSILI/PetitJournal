package com.app.model;

import java.io.Serializable;

import com.app.annotation.NonVide;

public class Article implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;

	@NonVide(mess = "Le titre doit contenir au moins 3 caractères !")
	private String titre;

	@NonVide(mess = "Le text de l'article doit contenir au moins 3 caractères !")
	private String text;

	private String date;
	private User user;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
