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
import com.app.form.ValidationForm;
import com.app.model.User;
import com.app.model.UserType;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/compte")
public class CompteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE = "/WEB-INF/views/commun/inscription.jsp";
	private static final String VUE_PAR = "/WEB-INF/views/commun/parametrage.jsp";
	private static final String VUE_NEW = "/WEB-INF/views/GUser/nouveauUserA.jsp";
	private static final String VUE_MOD = "/WEB-INF/views/GUser/modifierUserA.jsp";
	private static final String VUE_LIST = "/WEB-INF/views/GUser/listeUserA.jsp";
	private UserDao userDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompteServlet() {
		super();
		userDao = new UserDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		int idUs;
		if (action != null) {
			switch (action) {
			case "insc":// chargement de la vue inscription
				request.getRequestDispatcher(VUE).forward(request, response);
				break;
			case "param":// chargement de la vue parametrage du compte
							// utilisateur (modification)
				request.getRequestDispatcher(VUE_PAR).forward(request, response);
				break;
			case "new":
				request.getRequestDispatcher(VUE_NEW).forward(request, response);
				break;
			case "modif":
				if (request.getParameter("idUser") != null) {
					idUs = Integer.parseInt(request.getParameter("idUser"));
					User user = userDao.getUser(idUs);
					session.setAttribute("user", user);
					request.getRequestDispatcher(VUE_MOD).forward(request, response);
				}

				break;
			case "list":
				request.setAttribute("users", userDao.listerTout());
				request.getRequestDispatcher(VUE_LIST).forward(request, response);
				break;
			case "delete":
				if (request.getParameter("idUser") != null) {
					idUs = Integer.parseInt(request.getParameter("idUser"));
					userDao.supprimer(idUs);
					response.sendRedirect(request.getContextPath() + "/compte?action=list");
				}
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
		ConfigurablePasswordEncryptor passwordEncryptor = null;
		HttpSession session = request.getSession();
		User user = new User();
		UserType userType = new UserType();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "insc":
				if (request.getParameter("submit") != null) {
					String nom = request.getParameter("nom");
					String prenom = request.getParameter("prenom");
					String login = request.getParameter("login");
					String password = request.getParameter("password");

					// chiffrer le mot de passe avec SHA
					passwordEncryptor = new ConfigurablePasswordEncryptor();
					passwordEncryptor.setAlgorithm("SHA-256");
					passwordEncryptor.setPlainDigest(true);
					String motDePasseChiffre = passwordEncryptor.encryptPassword(password);

					// instancier l'objet ValidationForm
					ValidationForm form = new ValidationForm();

					// appeler les méthode de validation
					if (form.nonVide(User.class, "nom", nom)) {
						form.estUnNom(User.class, "nom", nom);
					}
					if (form.nonVide(User.class, "prenom", prenom)) {
						form.estUnNom(User.class, "prenom", prenom);
					}
					if (form.nonVide(User.class, "login", login)) {
						form.estUneChaine(User.class, "login", login);
					}
					if (form.nonVide(User.class, "password", password)) {
						form.estUnPasse(User.class, "password", password);
					}

					if (form.isValide()) {
						// modifier les valeur du user
						user.setNom(nom);
						user.setPrenom(prenom);
						user.setLogin(login);
						user.setPassword(motDePasseChiffre);
						userType.setId(2);
						user.setUserType(userType);
						// ajouter un utilisateur
						userDao.ajouter(user);
						session.setAttribute("userSession", user);
						response.sendRedirect(request.getContextPath() + "/");
					} else {
						request.setAttribute("form", form);
						request.getRequestDispatcher(VUE).forward(request, response);
					}

				}
				break;
			case "new":
				if (request.getParameter("submit") != null) {
					String nom = request.getParameter("nom");
					String prenom = request.getParameter("prenom");
					String login = request.getParameter("login");
					String password = request.getParameter("password");
					String type = request.getParameter("userType");
					System.out.println(type);
					// chiffrer le mot de passe avec SHA
					passwordEncryptor = new ConfigurablePasswordEncryptor();
					passwordEncryptor.setAlgorithm("SHA-256");
					passwordEncryptor.setPlainDigest(true);
					String motDePasseChiffre = passwordEncryptor.encryptPassword(password);

					// instancier l'objet ValidationForm
					ValidationForm form = new ValidationForm();
					// appeler les méthode de validation
					form.validSelect("userType", type);
					if (form.nonVide(User.class, "nom", nom)) {
						form.estUnNom(User.class, "nom", nom);
					}
					if (form.nonVide(User.class, "prenom", prenom)) {
						form.estUnNom(User.class, "prenom", prenom);
					}
					if (form.nonVide(User.class, "login", login)) {
						form.estUneChaine(User.class, "login", login);
					}
					if (form.nonVide(User.class, "password", password)) {
						form.estUnPasse(User.class, "password", password);
					}

					if (form.isValide()) {
						// modifier les valeur du user
						user.setNom(nom);
						user.setPrenom(prenom);
						user.setLogin(login);
						user.setPassword(motDePasseChiffre);
						int idUserType = Integer.parseInt(type);
						userType.setId(idUserType);
						user.setUserType(userType);
						// ajouter un utilisateur
						userDao.ajouter(user);
						response.sendRedirect(request.getContextPath() + "/compte?action=list");
					} else {
						request.setAttribute("form", form);
						request.getRequestDispatcher(VUE_NEW).forward(request, response);
					}
				}
				break;
			case "modif":
				if (request.getParameter("submit") != null) {
					User oldUser = (User) session.getAttribute("user");
					String nom = request.getParameter("nom");
					String prenom = request.getParameter("prenom");
					String login = request.getParameter("login");
					String password = request.getParameter("password");
					String type = request.getParameter("userType");

					// chiffrer le mot de passe avec SHA
					passwordEncryptor = new ConfigurablePasswordEncryptor();
					passwordEncryptor.setAlgorithm("SHA-256");
					passwordEncryptor.setPlainDigest(true);
					String motDePasseChiffre = passwordEncryptor.encryptPassword(password);

					// instancier l'objet ValidationForm
					ValidationForm form = new ValidationForm();

					// appeler les méthode de validation
					form.validSelect("userType", type);
					if (form.nonVide(User.class, "nom", nom)) {
						form.estUnNom(User.class, "nom", nom);
					}
					if (form.nonVide(User.class, "prenom", prenom)) {
						form.estUnNom(User.class, "prenom", prenom);
					}
					if (form.nonVide(User.class, "login", login)) {
						form.estUneChaine(User.class, "login", login);
					}

					if (form.nonVide(User.class, "password", password)) {
						form.estUnPasse(User.class, "password", password);
					}

					if (form.isValide()) {
						// modifier les valeur du user
						user.setId(oldUser.getId());
						user.setNom(nom);
						user.setPrenom(prenom);
						user.setLogin(login);
						user.setPassword(motDePasseChiffre);
						int idUserType = Integer.parseInt(type);
						userType.setId(idUserType);
						user.setUserType(userType);
						// modifier un utilisateur
						userDao.modifier(user);
						session.setAttribute("user", null);
						response.sendRedirect(request.getContextPath() + "/compte?action=list");
					} else {
						request.setAttribute("form", form);
						request.getRequestDispatcher(VUE_MOD).forward(request, response);
					}
				}
				break;
			case "param":
				if (request.getParameter("submit") != null) {
					User oldUser = (User) session.getAttribute("userSession");
					String nom = request.getParameter("nom");
					String prenom = request.getParameter("prenom");
					String login = request.getParameter("login");
					String password = request.getParameter("password");

					// chiffrer le mot de passe avec SHA
					passwordEncryptor = new ConfigurablePasswordEncryptor();
					passwordEncryptor.setAlgorithm("SHA-256");
					passwordEncryptor.setPlainDigest(true);
					String motDePasseChiffre = passwordEncryptor.encryptPassword(password);

					// instancier l'objet ValidationForm
					ValidationForm form = new ValidationForm();

					// appeler les méthode de validation
					if (form.nonVide(User.class, "nom", nom)) {
						form.estUnNom(User.class, "nom", nom);
					}
					if (form.nonVide(User.class, "prenom", prenom)) {
						form.estUnNom(User.class, "prenom", prenom);
					}
					if (form.nonVide(User.class, "login", login)) {
						form.estUneChaine(User.class, "login", login);
					}
					if (form.nonVide(User.class, "password", password)) {
						form.estUnPasse(User.class, "password", password);
					}

					if (form.isValide()) {
						// modifier les valeur du user
						user.setId(oldUser.getId());
						user.setNom(nom);
						user.setPrenom(prenom);
						user.setLogin(login);
						user.setPassword(motDePasseChiffre);
						user.setUserType(oldUser.getUserType());
						// modifier un utilisateur
						userDao.modifier(user);
						session.setAttribute("userSession", user);
						response.sendRedirect(request.getContextPath() + "/");
					} else {
						request.setAttribute("form", form);
						request.getRequestDispatcher(VUE_PAR).forward(request, response);
					}
				}
				break;
			default:
				break;
			}
		}

	}

}
