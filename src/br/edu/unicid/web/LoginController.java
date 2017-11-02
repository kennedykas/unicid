package br.edu.unicid.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.unicid.bean.Usuario;
import br.edu.unicid.dao.UsuarioDAO;

public class LoginController extends HttpServlet {

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
		request.setCharacterEncoding("UTF-8");
		request.getSession().setAttribute("status", 0);
		// a variável cmd indica o tipo de ação - incluir, alterar,
		// autenticar.....
		String cmd = request.getParameter("cmd");
		// cria um objeto dao - CRUD
		UsuarioDAO dao = new UsuarioDAO();
		// cria um objeto do tipo usuario
		Usuario usuario = new Usuario();

		try {

			if (cmd.equalsIgnoreCase("autenticar")) {
				System.out.println("chamado");
				boolean valida = dao.validaLogin(request.getParameter("login"),
						request.getParameter("senhalogin"));
				usuario = dao.procurarUsuario(request.getParameter("login"));

				if (valida) {
					request.setAttribute("usuario", usuario);
					System.out.println("CHARSET "
							+ request.getCharacterEncoding());
					request.getSession().setAttribute("cpf", usuario.getCpf());
					RequestDispatcher rd;
					// response.sendRedirect("menu.jsp");
					if (usuario.getStatus().equals("0")) {
						rd = request.getRequestDispatcher("cadastro.jsp");
						request.getSession().setAttribute("cpf",
								usuario.getCpf());
						request.getSession().setAttribute("nome",
								usuario.getNome());
						request.getSession().setAttribute("email",
								usuario.getEmail());
					} else {
						// rd = request.getRequestDispatcher("menu.jsp");
						response.setContentType("text/html;charset=utf-8");
						rd = request
								.getRequestDispatcher("/CarregaDadosPalestras");
					}
					rd.forward(request, response);
				} else {
					response.sendRedirect("/wecti/Erros/errologin.jsp");
				}

			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 *
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			processRequest(request, response);
		} catch (Exception ex) {
			Logger.getLogger(ServletUsuario.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}
}
