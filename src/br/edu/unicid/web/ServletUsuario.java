package br.edu.unicid.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.unicid.bean.Usuario;
import br.edu.unicid.dao.UsuarioDAO;

/**
 * Servlet implementation class ServletAlunos
 */
@WebServlet("/ServletUsuario")
public class ServletUsuario extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		response.setContentType("text/html;charset=UTF-8");

		request.getSession().setAttribute("sucesso", 0);
		request.getSession().setAttribute("cpf", "");
		request.getSession().setAttribute("nome", "");
		request.getSession().setAttribute("email", "");
		// a variável cmd indica o tipo de ação - incluir, alterar,
		// autenticar.....
		String cmd = request.getParameter("cmd");
		// cria um objeto dao - CRUD
		UsuarioDAO dao = new UsuarioDAO();
		// cria um objeto do tipo usuario
		Usuario usuario = new Usuario();
		if (cmd != null) {
			try {
				// inicializa os atributos da classe Usuario
				usuario.setCpf(request.getParameter("cpf"));
				usuario.setNome(request.getParameter("nome"));
				usuario.setEmail(request.getParameter("email"));
				usuario.setSenha(request.getParameter("senha"));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {

			// QrCodeClass qr = new QrCodeClass();
			RequestDispatcher rd = null;

			if (cmd.equalsIgnoreCase("cadastrar")) {
				rd = request.getRequestDispatcher("/cadastro.jsp");
			}

			if (cmd.equalsIgnoreCase("incluir")) {
				if (dao.procurarUsuario(usuario.getCpf()) != null) {
					response.sendRedirect("/Wecti/Erros/errocadastro.jsp");
					// rd =
					// request.getRequestDispatcher("/Erros/errocadastro.jsp");
				} else {
					dao.salvar(usuario);
					// qr.gerarQrcode(request.getParameter("cpf"));
					// rd = request.getRequestDispatcher("/cadastro.jsp");
					rd = request.getRequestDispatcher("/loginAluno.jsp");
					request.getSession().setAttribute("status", 1);
				}

			} else if (cmd.equalsIgnoreCase("alterar")) {
				// usuario = dao.procurarUsuario(request.getParameter("cpf"));

				// usuario.setCpf(request.getParameter("cpf"));
				// usuario.setNome(request.getParameter("nome"));
				// usuario.setEmail(request.getParameter("email"));
				// usuario.setSenha(request.getParameter("senha"));
				dao.atualizaUsuario(usuario);
				// rd = request.getRequestDispatcher("/cadastro.jsp");
				rd = request.getRequestDispatcher("/loginAluno.jsp");
				request.getSession().setAttribute("status", 1);
			}

			// executa a ação de direcionar para a página JSP
			rd.forward(request, response);
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
