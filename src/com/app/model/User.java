package com.app.model;

import java.io.Serializable;

import com.app.annotation.EstUnNom;
import com.app.annotation.EstUnPasse;
import com.app.annotation.NonVide;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;

	@NonVide(mess = "Le nom doit contenir au moins 3 caractères !")
	@EstUnNom(mess = "Le nom doit contenir que des lettres !")
	private String nom;

	@NonVide(mess = "Le prénom doit contenir au moins 3 caractères !")
	@EstUnNom(mess = "Le nom doit contenir que des lettres !")
	private String prenom;

	@NonVide(mess = "Le login doit contenir au moins 3 caractères !")
	private String login;

	@EstUnPasse(mess = "Le mot de passe doit contenir des chiffres, des minuscules et des majuscules entre 3 à 8 caractères !")
	private String password;

	private UserType userType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

}
