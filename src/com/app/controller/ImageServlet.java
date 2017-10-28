package com.app.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.dao.IllustrationDao;
import com.app.dao.IllustrationDaoImpl;
import com.app.model.Illustration;

/**
 * Servlet implementation class InscriptionServlet
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IllustrationDao illustrationDao;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();
		illustrationDao = new IllustrationDaoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// recuperation de l'id de l'illustration (image) a afficher
		if (request.getParameter("idImg") != null) {
			int idImg = Integer.parseInt(request.getParameter("idImg"));
			Illustration illustration = new Illustration();
			//recuperation de l'illustration en question
			illustration = illustrationDao.getIllustration(idImg);
			//recuperation de l'inputstream a partir de la bdd en utilisant un getter 
			InputStream is = illustration.getImage();
			//ecriture de l'image
			response.setContentType("image/jpg");
			OutputStream o = response.getOutputStream();
			int c;
			while ((c = is.read()) != -1) {
				o.write(c);
			}
			o.flush();
			o.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
