package br.edu.unicid.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.unicid.bean.Palestra;
import br.edu.unicid.bean.Usuario;
import br.edu.unicid.dao.PalestraDAO;
import br.edu.unicid.dao.PalestraUserDAO;

public class CarregaDadosPalestras extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		response.setContentType("text/html;charset=UTF-8");

		Usuario usuario = new Usuario();

		usuario = (Usuario) request.getAttribute("usuario");

		String cpf = (String) request.getAttribute("cpf");

		// usuario = (Usuario) request.getAttribute("usuario");

		RequestDispatcher rd = null;

		Palestra palestra1 = new Palestra();

		PalestraDAO paledao = new PalestraDAO();

		PalestraUserDAO daopu = new PalestraUserDAO();

		if (usuario.getStatus().equals("1")) {

			Map<String, Boolean> mapTemVaga = paledao.getMapTemVaga();

			request.setAttribute("usuario", usuario);
			request.setAttribute("mapTemVaga", mapTemVaga);
			response.setContentType("text/html;charset=utf-8");
			rd = request.getRequestDispatcher("menu.jsp");
			rd.forward(request, response);

		} else if (usuario.getStatus().equalsIgnoreCase("2")) {

			List<Palestra> listPalestra = new ArrayList<>();
			listPalestra = daopu.retornaPalestra(usuario.getCpf());
			request.getSession().setAttribute("usuario", usuario);
			request.setAttribute("listPalestra", listPalestra);
			rd = request.getRequestDispatcher("menu_1.jsp");
			rd.forward(request, response);

		}

	}

	// <editor-fold defaultstate="collapsed"
	// desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(CarregaDadosPalestras.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(CarregaDadosPalestras.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>

}
