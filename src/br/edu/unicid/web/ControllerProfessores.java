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
	private static final String PAGE_NEW_PROFSSOR        = "/create/novoProfessor";
	private static final String PAGE_EMAIL_SENT          = "/user-tools/emailEnviado";
	private static final String PAGE_LIST_QUESTIONS      = "/list/listaQuestoes";
	private static final String PAGE_LOGIN_PROFSSOR      = "/login/loginProfessor";
	private static final String PAGE_UPDATE_PROFESSOR    = "/update/alterarProfessor";
	private static final String PAGE_RECOVER_PROF_PASS   = "/user-tools/recuperarSenhaProfessor";
	private static final String WRONG_EMAIL              = "EMAIL incorreto.";
	private static final String WRONG_PASS               = "SENHA incorreta.";
	private static final String FORGET_CHECK_RECAPTCHA   = "QUASE só falta você dizer que não é um robô!";
	private static final String RECAPTCHA_FAILED         = "CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!";
	private static final String EMAIL_WAS_SENT_WITH_PASS = "ENVIAMOS um email com a sua senha, para o endereço cadastrado. dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String NAME_NOT_FOUND           = "NOME não encontrado (maiúsculas e minúsculas não interferem), dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String FACE_MESSAGES_ID         = "messages";
	private static final String SUCCESS                  = "ok";
	
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
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_NEW_PROFSSOR;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(RECAPTCHA_FAILED));
			return PAGE_NEW_PROFSSOR;
		}	
				
		dao = new ProfessorDAO();
		
		if(dao.salvar(professor)) { // IF PROFESSOR SALVO COM SUCESSO ELE RECEBE UM EMAIL
			this.email.sendEmailVerificacao(this.professor); // PASSANDO O PROFESSOR PARA Q ELE RECEBA O EMAIL
			this.renderEmailProfessor = true; // MSG SERA RENDERIZADA PARA O PROFESSOR
			return PAGE_EMAIL_SENT; 
		}
		return PAGE_NEW_PROFSSOR;
	}
	
	// LOGIN
	public String login() {

		this.dao = new ProfessorDAO();
		String login = this.dao.login(this.professor);
	
		// LOGIN EFETUADO
		if(login.equals(SUCCESS))  
			return PAGE_LIST_QUESTIONS;
		
		// EMAIL ERRADO
		else if(login.equals("email")) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(WRONG_EMAIL));
				return PAGE_LOGIN_PROFSSOR;
			
			// SENHA INCORRETA	
			} else if(login.equals("senha")) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(WRONG_PASS));
					return PAGE_LOGIN_PROFSSOR;
				
				// EMAIL NAO VERIFICADO		
				} else if(login.equals("unverified")) { 
						this.email.sendEmailVerificacao(this.professor); // ENVIANDO EMAIL NOVAMENTE
						this.renderEmailProfessor = true; // MSG SERA RENDERIZADA PARA O PROFESSOR
						return PAGE_EMAIL_SENT;
					} else 
						return PAGE_LOGIN_PROFSSOR;
	}
	
	// AUTENTICA 
	public String autenticaProfessor(String info) {
		String[] infos = info.split(" ");
		this.professor.setData(infos[0]);
		this.professor.setEmailProfessor(infos[1]);

		this.dao = new ProfessorDAO();
		
		if(this.dao.autenticar(this.professor))
			return "Pronto!";
		
		return "Algo não aconteceu como o esperado!";		
	}
	
	public String updateProfessorInfo() {

		dao = new ProfessorDAO();
		
		return (dao.updateProfessor(professor)) ? PAGE_LIST_QUESTIONS : PAGE_UPDATE_PROFESSOR;		
	}
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_RECOVER_PROF_PASS;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(RECAPTCHA_FAILED));
			return PAGE_RECOVER_PROF_PASS;
		}	
		
		this.dao = new ProfessorDAO();
		String resultado = this.dao.recuperarSenha(this.professor); 
		
		if(resultado.equals(SUCCESS)) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.professor); // ENVIANDO EMAIL SENHA
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(EMAIL_WAS_SENT_WITH_PASS));
		} else if(resultado.equals("nome")) 
				ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(NAME_NOT_FOUND));

		return PAGE_RECOVER_PROF_PASS;		
	}
	
	// QUIT FROM APP
	public String quit() {
		init();
		return PAGE_LOGIN_PROFSSOR;
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
