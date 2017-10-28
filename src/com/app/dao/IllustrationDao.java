package com.app.dao;

import java.util.List;

import com.app.model.Illustration;

public interface IllustrationDao {

	public void ajouter(Illustration illustration);

	public void supprimer(int id);

	public void modifier(Illustration illustration);

	public List<Illustration> listerTout();

	public List<Illustration> listerParIdArt(int idArt);

	public Illustration getIllustration(int id);

}
