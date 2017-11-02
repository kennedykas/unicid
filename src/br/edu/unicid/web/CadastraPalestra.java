package br.edu.unicid.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.unicid.bean.IdPalestra;
import br.edu.unicid.bean.Palestra;
import br.edu.unicid.bean.Usuario;
import br.edu.unicid.dao.PalestraDAO;
import br.edu.unicid.dao.PalestraUserDAO;
import br.edu.unicid.dao.UsuarioDAO;

public class CadastraPalestra extends HttpServlet {

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		response.setContentType("text/html;charset=UTF-8");

		request.getSession().setAttribute("status1", 0);
		request.setCharacterEncoding("UTF-8");
		Usuario user = new Usuario();
		user = (Usuario) request.getAttribute("usuario");

		// cria um objeto dao - da Palestra e Usuario
		PalestraUserDAO daopu = new PalestraUserDAO();
		// cria um objeto dao - do Usuario
		UsuarioDAO daouser = new UsuarioDAO();
		// cria um objeto dao - da Palestra
		PalestraDAO daopale = new PalestraDAO();

		// pega o request sessao de CPF
		String cpf = (String) request.getSession().getAttribute("cpf");

		// cria um objeto do tipo usuario
		Usuario usuario = new Usuario();

		// cria objeto palestra para view
		Palestra palestra = new Palestra();
		IdPalestra idpale = new IdPalestra();

		// cria objeto palestra para DAO
		Palestra pale = new Palestra();

		// retorna usuario
		usuario = daouser.procurarUsuario(cpf);

		// coloca valores das palestras selecionadas do objeto palestra
		idpale.setIdGeral(request.getParameter("palestra"));
		idpale.setIdManha1(request.getParameter("palestraM"));
		idpale.setIdManha2(request.getParameter("oficinaM"));
		idpale.setIdNoite1(request.getParameter("palestraN"));
		idpale.setIdNoite2(request.getParameter("oficinaN"));
		idpale.setIdGeralM(request.getParameter("palestraGeralM"));
		idpale.setIdGeralN(request.getParameter("palestraGeralN"));
		//

		RequestDispatcher rd;

		// Palestra geral manha dia 03/05
		RequestDispatcher rdE = null;

		if (idpale.getIdGeral() != null) {
			int num = Integer.parseInt(idpale.getIdGeral());
			pale = daopale.procurarPalestra(num);

			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdGeral());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		if (idpale.getIdManha1() != null) {

			// Palestra Manha 1
			int num2 = Integer.parseInt(idpale.getIdManha1());
			if (num2 != 0) {
				pale = daopale.procurarPalestra(num2);
			}

			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdManha1());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}

		}

		if (idpale.getIdManha2() != null) {
			// Palestra Manha 2
			int num3 = Integer.parseInt(idpale.getIdManha2());
			if (num3 != 0) {
				pale = daopale.procurarPalestra(num3);
			}

			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdManha2());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		if (idpale.getIdNoite1() != null) {
			// Palestra Noite 1
			int num4 = Integer.parseInt(idpale.getIdNoite1());
			if (num4 != 0) {
				pale = daopale.procurarPalestra(num4);
			}
			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdNoite1());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		if (idpale.getIdNoite2() != null) {
			// Palestra Noite 2
			int num5 = Integer.parseInt(idpale.getIdNoite2());
			if (num5 != 0) {
				pale = daopale.procurarPalestra(num5);
			}

			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdNoite2());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		if (idpale.getIdGeralM() != null) {
			// Palestra Geral Manha
			int num6 = Integer.parseInt(idpale.getIdGeralM());
			if (num6 != 0) {
				pale = daopale.procurarPalestra(num6);
			}

			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdGeralM());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		if (idpale.getIdGeralN() != null) {
			// Palestra Geral Noite
			int num7 = Integer.parseInt(idpale.getIdGeralN());
			if (num7 != 0) {
				pale = daopale.procurarPalestra(num7);
			}
			if ((pale.getContador() + 1) <= pale.getVagas()
					&& usuario.getStatus().equals("1")) {
				// atualiza contado de vagas da palestra
				daopale.atualizaContador(pale.getId_palestra(),
						pale.getContador() + 1);
				// Cadastra o usuario na palestra
				daopu.salvarPalestra(usuario, idpale.getIdGeralN());
				System.out.println("salvo com  sucesso");
			} else {
				response.sendRedirect("/Wecti/Erros/erropalestra.jsp");
				rdE.forward(request, response);
				System.out.println("nao pode salvar");
			}
		}

		daouser.atualizaUsuarioPalestra(usuario);
		request.setAttribute("usuario", user);
		rd = request.getRequestDispatcher("index.jsp");
		request.getSession().setAttribute("sucesso", 1);

		rd.forward(request, response);

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
			Logger.getLogger(CadastraPalestra.class.getName()).log(
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
			Logger.getLogger(CadastraPalestra.class.getName()).log(
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
