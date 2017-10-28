package com.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.dao.ArticleDao;
import com.app.dao.ArticleDaoImpl;
import com.app.form.ValidationForm;
import com.app.model.Article;
import com.app.model.User;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/article")
public class ArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE = "/WEB-INF/views/article/nouveauArticle.jsp";
	private static final String VUE_MOD = "/WEB-INF/views/article/modifierArticle.jsp";
	private static final String VUE_LISTE = "/WEB-INF/views/article/listeToutArticles.jsp";
	private static final String VUE_MINDE = "/WEB-INF/views/article/listeMesArticles.jsp";
	private ArticleDao articleDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ArticleServlet() {
		super();
		articleDao = new ArticleDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if (action != null) {
			switch (action) {
			case "add":// chargement de la vue ajouter article
				request.getRequestDispatcher(VUE).forward(request, response);
				break;
			case "edit":// modifier article (Get)
				if (request.getParameter("idArt") != null) {
					int idArt = Integer.parseInt(request.getParameter("idArt"));
					// recupérer les information de l'article a modifier dans
					// une session
					session.setAttribute("article", articleDao.getArticle(idArt));
					// chargement de la vue modifier article
					request.getRequestDispatcher(VUE_MOD).forward(request, response);
				}

				break;
			case "delete":// supprimer article
				if (request.getParameter("idArt") != null) {
					// recupérer l'ID de l'article a supprimer
					int idArt = Integer.parseInt(request.getParameter("idArt"));
					articleDao.supprimer(idArt);
					// redirection ver la page d'accieul
					response.sendRedirect(request.getContextPath() + "/");
				}
				break;
			case "list":// liste de tt les articles
				// lister la liste des articles
				List<Article> articles = articleDao.listerTout();
				// mettre la liste dans un attribut de requete
				request.setAttribute("listeArticles", articles);
				// charger la page de la liste des articles
				request.getRequestDispatcher(VUE_LISTE).forward(request, response);
				break;
			case "list-minde":// liste des articles de l'utilisateur connecté
				User user = (User) session.getAttribute("userSession");
				if (user != null) {
					int idUser = user.getId();
					List<Article> articlesMinde = articleDao.listerParUser(idUser);
					request.setAttribute("listeArticlesMinde", articlesMinde);
					request.getRequestDispatcher(VUE_MINDE).forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + "/sessionUtilisateur?tache=conn");
				}

				break;

			default:
				response.sendRedirect(request.getContextPath() + "/");
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

		HttpSession session = request.getSession();
		Article article = new Article();
		String action = request.getParameter("action");
		if (action != null) {
			switch (action) {
			case "add":
				if (request.getParameter("submit") != null) {
					// recuperation des valuers saisies
					String titre = request.getParameter("titre");
					String text = request.getParameter("text");
					// instancier un objet ValidationForm
					ValidationForm form = new ValidationForm();
					// valider les champs
					form.nonVide(Article.class, "titre", titre);
					form.nonVide(Article.class, "text", text);

					if (form.isValide()) {
						// mettre a jour les valeur de l'objet artice
						article.setTitre(titre);
						article.setText(text);
						User user = new User();
						// recuperer le user dans la session
						user = (User) session.getAttribute("userSession");
						// mettre a jour les informations de l'auteur de
						// l'article
						article.setUser(user);
						// ajouter a la BDD
						articleDao.ajouter(article);
						// redirection vers la page d'accieul
						response.sendRedirect(request.getContextPath() + "/");

					} else {
						request.setAttribute("form", form);
						request.getRequestDispatcher(VUE).forward(request, response);
					}
				}
				break;
			case "edit":
				if (request.getParameter("submit") != null) {
					if (request.getParameter("idArt") != null) {
						// recuperation de l'id de l'article
						int idArt = Integer.parseInt(request.getParameter("idArt"));
						// recuperation des données saisies
						String titre = request.getParameter("titre");
						String text = request.getParameter("text");
						// validation du formulaire
						ValidationForm form = new ValidationForm();
						form.nonVide(Article.class, "titre", titre);
						form.nonVide(Article.class, "text", text);

						if (form.isValide()) {
							// mettre à jour l'objet article
							article.setId(idArt);
							article.setTitre(titre);
							article.setText(text);
							// modification des informations
							articleDao.modifier(article);
							// l'attribut de session article a null
							session.setAttribute("article", null);
							// redirection vers la page d'accieul
							response.sendRedirect(request.getContextPath() + "/");
						} else {
							request.setAttribute("form", form);
							request.getRequestDispatcher(VUE_MOD).forward(request, response);
						}
					}
				}
				break;

			default:

				break;
			}
		}

	}

}
