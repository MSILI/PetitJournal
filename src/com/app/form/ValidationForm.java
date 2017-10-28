package com.app.form;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.regex.Pattern;

import com.app.annotation.EstUnNom;
import com.app.annotation.EstUnPasse;
import com.app.annotation.EstUneChaine;
import com.app.annotation.NonVide;

public class ValidationForm {

	boolean valide = true;
	Hashtable<String, String> valeurs = new Hashtable<String, String>();
	Hashtable<String, String> erreurs = new Hashtable<String, String>();

	// methode qui valide les champs vide
	public boolean nonVide(Class c, String param, String val) {
		boolean res = true;
		try {
			Field f = c.getDeclaredField(param);
			NonVide an = f.getDeclaredAnnotation(NonVide.class);
			String mess = an.mess();
			valeurs.put(param, val);
			if ((val == null) || (val.trim().length() < 3)) {
				erreurs.put(param, mess);
				res = false;
				valide = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// methode qui valide les nom et les prénom de personnes
	public boolean estUnNom(Class c, String param, String val) {

		boolean res = true;
		String regexp = "^[A-Za-zàâãèêé ]+$";
		try {
			Field f = c.getDeclaredField(param);
			EstUnNom an = f.getDeclaredAnnotation(EstUnNom.class);
			String mess = an.mess();

			valeurs.put(param, val);

			if (!Pattern.compile(regexp).matcher(val).matches()) {
				erreurs.put(param, mess);
				res = false;
				valide = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// methode qui accepte que des lettre et de chiffre et des espaces
	public boolean estUneChaine(Class c, String param, String val) {

		boolean res = true;
		String regexp = "^[A-Za-z0-9àâãèêé ]+$";
		try {
			Field f = c.getDeclaredField(param);
			EstUneChaine an = f.getDeclaredAnnotation(EstUneChaine.class);
			String mess = an.mess();

			valeurs.put(param, val);

			if (!Pattern.compile(regexp).matcher(val).matches()) {
				erreurs.put(param, mess);
				res = false;
				valide = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	// methode qui valide les mot de passes
	public boolean estUnPasse(Class c, String param, String val) {

		boolean res = true;
		String regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{3,8}$";
		try {
			Field f = c.getDeclaredField(param);
			EstUnPasse an = f.getDeclaredAnnotation(EstUnPasse.class);
			String mess = an.mess();
			valeurs.put(param, val);
			if (!Pattern.compile(regexp).matcher(val).matches()) {
				erreurs.put(param, mess);
				res = false;
				valide = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean validSelect(String param, String val) {
		boolean res = true;
		try {
			if ((val == null) || (val.trim().length() == 0)) {
				erreurs.put(param, "Veuillez selectionnez un type utilisateur !");
				res = false;
				valide = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean isValide() {
		return valide;
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public Hashtable<String, String> getValeurs() {
		return valeurs;
	}

	public void setValeurs(Hashtable<String, String> valeurs) {
		this.valeurs = valeurs;
	}

	public Hashtable<String, String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(Hashtable<String, String> erreurs) {
		this.erreurs = erreurs;
	}
}
