package br.edu.unicid.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;

import br.edu.unicid.bean.Professor;
import br.edu.unicid.constants.Constants;
import br.edu.unicid.dao.ProfessorDAO;
import br.edu.unicid.util.Email;
import br.edu.unicid.util.VerifyRecaptcha;

@ManagedBean(name="controllerProfessores")
@SessionScoped
public class ControllerProfessores {

	// AUXILIAR PARA RENDERIZAR MSG DE EMAIL ENVIADO
	private boolean      renderEmailProfessor = Boolean.FALSE; 
	private Professor    professor;
	private ProfessorDAO dao;
	private Email        email;
	private String       gRecaptchaResponse;
	
	public ControllerProfessores() {}
	
	@PostConstruct
	public void init() {
		this.professor = new Professor();
		this.email = new Email();
	}
	
	// SAVE
	public String save() throws IOException {

		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.FORGET_CHECK_RECAPTCHA));
			return Constants.PAGE_NEW_PROFSSOR;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.RECAPTCHA_FAILED));
			return Constants.PAGE_NEW_PROFSSOR;
		}	
				
		dao = new ProfessorDAO();
		
		if(dao.salvar(professor)) { // IF PROFESSOR SALVO COM SUCESSO ELE RECEBE UM EMAIL
			this.email.sendEmailVerificacao(this.professor); // PASSANDO O PROFESSOR PARA Q ELE RECEBA O EMAIL
			this.renderEmailProfessor = true; // MSG SERA RENDERIZADA PARA O PROFESSOR
			return Constants.PAGE_EMAIL_SENT; 
		}
		return Constants.PAGE_NEW_PROFSSOR;
	}
	
	// LOGIN
	public String login() {

		this.dao = new ProfessorDAO();
		String login = this.dao.login(this.professor);
	
		// LOGIN EFETUADO
		if(login.equals(Constants.SUCCESS))  
			return Constants.PAGE_LIST_QUESTIONS;
		
		// EMAIL ERRADO
		else if(login.equals(Constants.EMAIL)) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.WRONG_EMAIL));
				return Constants.PAGE_LOGIN_PROFSSOR;
			
			// SENHA INCORRETA	
			} else if(login.equals(Constants.PASSWORD)) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.WRONG_PASS));
					return Constants.PAGE_LOGIN_PROFSSOR;
				
				// EMAIL NAO VERIFICADO	THEN WE SEND ANOTHER ONE	
				} else if(login.equals(Constants.UNVERIFIED)) { 
						this.email.sendEmailVerificacao(this.professor);
						this.renderEmailProfessor = Boolean.TRUE; 
						return Constants.PAGE_EMAIL_SENT;
					} else 
						return Constants.PAGE_LOGIN_PROFSSOR;
	}
	
	// AUTENTICA 
	public String autenticaProfessor(String info) {
		String[] infos = info.split(" ");
		this.professor.setData(infos[0]);
		this.professor.setEmailProfessor(infos[1]);

		this.dao = new ProfessorDAO();
		
		if(this.dao.autenticar(this.professor))
			return Constants.READY;
		
		return Constants.SOMETHING_WENT_WRONG;		
	}
	
	public String updateProfessorInfo() {

		dao = new ProfessorDAO();
		
		return (dao.updateProfessor(professor)) ? Constants.PAGE_LIST_QUESTIONS : Constants.PAGE_UPDATE_PROFESSOR;		
	}
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.FORGET_CHECK_RECAPTCHA));
			return Constants.PAGE_RECOVER_PROF_PASS;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.RECAPTCHA_FAILED));
			return Constants.PAGE_RECOVER_PROF_PASS;
		}	
		
		this.dao = new ProfessorDAO();
		String resultado = this.dao.recuperarSenha(this.professor); 
		
		if(resultado.equals(Constants.SUCCESS)) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.professor); // ENVIANDO EMAIL SENHA
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.EMAIL_WAS_SENT_WITH_PASS));
		} else if(resultado.equals("nome")) 
				ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.NAME_NOT_FOUND));

		return Constants.PAGE_RECOVER_PROF_PASS;		
	}
	
	// QUIT FROM APP
	public String quit() {
		init();
		return Constants.PAGE_LOGIN_PROFSSOR;
	}
	
	// RENDERIZADOR DE ICONE DE EMAIL 
	public boolean renderEmail(String email) {
		return (this.professor.getEmailProfessor().contains(email)) ? true : false;
	}
	
	// LISTA DE PROFESSORES DO TIPO SELECTITEM
	public List<SelectItem> professores() {
		dao = new ProfessorDAO();

		List<SelectItem> items = new ArrayList<>();
		List<Professor> professorList = dao.professores();
	    
		for(Professor professor: professorList)
	    	items.add(new SelectItem(professor.getCodigo(), professor.getNomeProfessor()));

		return items;
	}

	// Getters and setters
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public boolean isRenderEmailProfessor() {
		return renderEmailProfessor;
	}
	public void setRenderEmailProfessor(boolean renderEmailProfessor) {
		this.renderEmailProfessor = renderEmailProfessor;
	}
}
