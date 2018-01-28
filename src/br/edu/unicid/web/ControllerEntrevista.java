package br.edu.unicid.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpServletRequest;

import br.edu.unicid.bean.Entrevista;
import br.edu.unicid.constants.Constants;
import br.edu.unicid.dao.EntrevistaDAO;
import br.edu.unicid.dao.ProvaDAO;
import br.edu.unicid.util.Email;
import br.edu.unicid.util.VerifyRecaptcha;

@ManagedBean(name="controllerEntrevista")
@SessionScoped
public class ControllerEntrevista {

	private Entrevista    entrevista;
	private EntrevistaDAO dao;
	private Email         email;
	private Date          date;
	private String        userRecaptchaResponse;
	
	private DataModel<Entrevista> listaEntrevistas;
	
	private final DateFormat DATA = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	public ControllerEntrevista() {}
	
	@PostConstruct
	public void init() {
		entrevista = new Entrevista();
		email = new Email();
	}
	
	public String save() throws IOException {
								
//		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
//		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
//		userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
//		if(userRecaptchaResponse.isEmpty()) { 
//			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.FORGET_CHECK_RECAPTCHA));
//			return Constants.PAGE_NEW_INTERVIEW;
//		}
		// RECAPTCHA ROBO (false = robot / true = people)
//		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
//			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.RECAPTCHA_FAILED));
//			return Constants.PAGE_NEW_INTERVIEW;
//		}		
		
		date = new Date();
		entrevista.setData(DATA.format(date)); // SETA DATA EM QUE O ALUNO MARCOU A ENTREVISTA
		
		dao = new EntrevistaDAO();
		
		if(dao.salvar(entrevista)) {
			
			email.sendEmailEntrevista(entrevista);

			return Constants.PAGE_SUCCESS_INTERVIEW;
			
		}
		
		return Constants.PAGE_NEW_INTERVIEW;
	}
	
	public void excluir() {
		dao = new EntrevistaDAO();

		if(dao.excluir(entrevista.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage("Prova excluida!"));				
		}
	}
	
	public void findInterviews() {
		
		dao = new EntrevistaDAO();
		
		List<Entrevista> lista = dao.obterTodasEntrevistas();
		
		listaEntrevistas = new ListDataModel<>(lista);
	}

	// GET ROW DATA
	public void selecionarRegistro() {
		entrevista = listaEntrevistas.getRowData();
	}
	
	// Getters and setters
	public DataModel<Entrevista> getListaEntrevistas() {
		return listaEntrevistas;
	}
	public Entrevista getEntrevista() {
		return entrevista;
	}
	public void setEntrevista(Entrevista entrevista) {
		this.entrevista = entrevista;
	}

}
