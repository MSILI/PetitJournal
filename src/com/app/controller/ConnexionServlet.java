package com.app.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.app.dao.UserDao;
import com.app.dao.UserDaoImpl;
import com.app.model.User;

/**
 * Servlet implementation class Connexion
 */
@WebServlet("/sessionUtilisateur")
public class ConnexionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE_CONN = "/WEB-INF/views/commun/connexion.jsp";
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ConnexionServlet() {
		super();
		userDao = new UserDaoImpl();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tache = request.getParameter("tache");
		HttpSession session = request.getSession();
		if (tache != null) {
			switch (tache) {
			case "conn":// connexion
				// chargement de la vue de connexion
				request.getRequestDispatcher(VUE_CONN).forward(request, response);
				break;
			case "dec":// deconnexion
				// tuer la session et redirection vers la page d'accieul
				session.invalidate();
				response.sendRedirect(this.getServletContext().getContextPath() + "/");
				break;
			default:

				break;
			}
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String tache = request.getParameter("tache");
		HttpSession session = request.getSession();
		if (tache != null) {
			switch (tache) {
			case "conn":// connexion
				User user = new User();
				String login = request.getParameter("login");
				String password = request.getParameter("password");
				// chiffrer le mot de passe saisi pour le comparer avec celui de
				// la BDD
				ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
				passwordEncryptor.setAlgorithm("SHA-256");
				passwordEncryptor.setPlainDigest(true);
				String motDePasseChiffre = passwordEncryptor.encryptPassword(password);
				// chercher si l'utilisateur existe dans la bdd
				user = userDao.getUserWithLoginAndpassword(login, motDePasseChiffre);
				if (user != null) {
					// si oui alors on lui crée une session et on le redirige
					// vers la page d'accieul
					session.setAttribute("userSession", user);
					response.sendRedirect(request.getContextPath() + "/");
				} else {
					//sinon on affiche un msg d'erreur
					request.setAttribute("message", "Login ou mot de passe incorrecte!");
					request.getRequestDispatcher(VUE_CONN).forward(request, response);
				}

				break;
			default:

				break;
			}
		}
	}

}
