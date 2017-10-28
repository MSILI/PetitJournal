package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.app.dao.ArticleDao;
import com.app.dao.ArticleDaoImpl;
import com.app.dao.IllustrationDao;
import com.app.dao.IllustrationDaoImpl;
import com.app.form.ValidationForm;
import com.app.model.Article;
import com.app.model.Illustration;
import com.app.model.User;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/illustration")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 3, maxFileSize = 1024 * 1024 * 50, maxRequestSize = 1024 * 1024 * 5
		* 50)
public class IllustrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VUE = "/WEB-INF/views/illustration/nouvelleIllustration.jsp";
	private static final String VUE_LISTE = "/WEB-INF/views/illustration/listeIllustrations.jsp";
	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	private IllustrationDao illustrationDao;
	private ArticleDao articleDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IllustrationServlet() {
		super();
		illustrationDao = new IllustrationDaoImpl();
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
			case "add":// chargement de la page d'ajout d'une illustration par
						// un passage d'un paramètre idArt a fin de savoir a
						// quel article on se réfere
				if (request.getParameter("idArt") != null) {
					int idArt = Integer.parseInt(request.getParameter("idArt"));
					session.setAttribute("idArt", idArt);
					request.getRequestDispatcher(VUE).forward(request, response);
				}
				break;
			case "delete":// supprimer un article
				if (request.getParameter("idIl") != null && request.getParameter("idArt") != null) {
					int idIl = Integer.parseInt(request.getParameter("idIl"));
					int idArt = Integer.parseInt(request.getParameter("idArt"));
					illustrationDao.supprimer(idIl);
					// redirection vers la liste des illustration de l'article
					// concerné
					response.sendRedirect(request.getContextPath() + "/illustration?action=list&idArt=" + idArt);
				}
				break;
			case "list"://lister tt les illustrations d'un article particulier
				if (request.getParameter("idArt") != null) {
					int idArt = Integer.parseInt(request.getParameter("idArt"));
					request.setAttribute("article", articleDao.getArticle(idArt));
					request.setAttribute("listeIllustrations", illustrationDao.listerParIdArt(idArt));
					request.getRequestDispatcher(VUE_LISTE).forward(request, response);
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
		Illustration illustration = new Illustration();
		HttpSession session = request.getSession();
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(MEMORY_THRESHOLD);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(MAX_FILE_SIZE);
			upload.setSizeMax(MAX_REQUEST_SIZE);
			String titre = null;
			try {
				@SuppressWarnings("unchecked")
				List<FileItem> formItems = upload.parseRequest(request);
				if (formItems != null && formItems.size() > 0) {
					for (FileItem item : formItems) {
						if (!item.isFormField()) {
							InputStream image = item.getInputStream();
							illustration.setImage(image);
						} else {
							String name = item.getFieldName();
							if (name.equalsIgnoreCase("titre")) {
								titre = item.getString();
							}

						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			ValidationForm form = new ValidationForm();
			form.nonVide(Illustration.class, "titre", titre);
			if (form.isValide()) {
				User user = (User) session.getAttribute("userSession");
				illustration.setUser(user);
				illustration.setTitre(titre);
				Article article = new Article();
				int id = (int) session.getAttribute("idArt");
				article.setId(id);
				illustration.setArticle(article);
				illustrationDao.ajouter(illustration);
				session.setAttribute("idArt", null);
				response.sendRedirect(request.getContextPath() + "/illustration?action=list&idArt="
						+ illustration.getArticle().getId());
			} else {
				request.setAttribute("form", form);
				request.getRequestDispatcher(VUE).forward(request, response);
			}
		}

	}

}
