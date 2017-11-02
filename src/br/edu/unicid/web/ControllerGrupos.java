package br.edu.unicid.web;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import br.edu.unicid.bean.Grupo;
import br.edu.unicid.dao.GrupoDAO;
import br.edu.unicid.util.VerifyRecaptcha;

@ManagedBean(name="controllerGrupos")
@SessionScoped
public class ControllerGrupos {

	private GrupoDAO dao;
	private Grupo grupo;
	private DataModel<Grupo> listaGrupo;
	private String gRecaptchaResponse;
	
	public ControllerGrupos() {}

	@PostConstruct
	public void init() {
		grupo = new Grupo();
	}
	
	// SAVE
	public String save() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage("QUASE só falta você dizer que não é um robô!"));
			return "/create/novoGrupo";
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage("CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!"));
			return "/create/novoGrupo";
		}	
		
		arquivo(); // SETA OS DADOS DO ARQUIVO NO BEAN
		
		dao = new GrupoDAO();

		return (dao.salvar(grupo)) ? "/login/loginGrupo" : "/create/novoGrupo";
	}
	
	public void arquivo() throws IOException {
		InputStream input = grupo.getArquivo().getInputStream();
		byte[] file = IOUtils.toByteArray(input); // Apache commons IO.
		grupo.setBytes(file);
		grupo.setNomeArquivo(grupo.getArquivo().getSubmittedFileName());
	}
	
	public void alterar() throws IOException {
		arquivo();
		dao = new GrupoDAO();
		dao.alterar(grupo); 
	}
	
	// LOGIN
	public String login() {
		this.dao = new GrupoDAO();
		String login = this.dao.login(this.grupo);

		// LOGIN EFETUADO  
		if(login.equals("ok")) 
			return "/see/grupoAluno";
		
		// NOME DO GRUPO ERRADO
		else if(login.equals("nome")) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage("messages", new FacesMessage("NOME DA EQUIPE incorreto (maiúsculas e minusculas não fazem diferença tanto faz ex: Equipe como EQUIPE), tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
			
			// SENHA INCORRETA
			} else { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage("messages", new FacesMessage("SENHA incorreta, tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
			}
		return "/login/loginGrupo";
	}
	
	public String quit() {
		init();
		return "/login/loginGrupo";
	}
	
	// Metodo para fazer o download do file
	public void download(boolean download) throws Exception {
		// Prepare.
	    FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

	    // Initialize response.
	    response.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
	    response.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.
	    if(download)
	    	response.setHeader("Content-disposition", "attachment; filename=" + grupo.getNomeArquivo()); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.
	    else
	    	response.setHeader("Content-disposition", "inline; filename=" + grupo.getNomeArquivo()); // The Save As popup magic is done here. You can give it any filename you want, this only won't work in MSIE, it will use current request URL as filename instead.
	    
	    // Write file to response.
	    OutputStream output = response.getOutputStream();
	    output.write(grupo.getBytes());
	    output.close();

	    // Inform JSF to not take the response in hands.
	    facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.	
	}
	
	// Obtem os dados da linha selecionada na lista de grupos
	public void selecionarRegistro() {
		this.grupo = listaGrupo.getRowData(); // Obtendo as informacoes da linha selecionada
	}

	public DataModel<Grupo> getListaGrupos() {
		dao = new GrupoDAO();

		List<Grupo> lista = dao.todosGrupos();
		listaGrupo = new ListDataModel<Grupo>(lista);
		
		return listaGrupo;
	}
	
	public void setListaTrabalho(DataModel<Grupo> listaTrabalho) {
		this.listaGrupo = listaTrabalho;
	}

	// GETTERS AND SETTERS
	public Grupo getGrupo() {
		return grupo;
	}
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}
}
