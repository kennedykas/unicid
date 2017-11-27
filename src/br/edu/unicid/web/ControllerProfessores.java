package br.edu.unicid.web;

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

	private Professor    professor;
	private ProfessorDAO dao;
	private Email        email;
	// AUXILIAR PARA RENDERIZAR MSG DE EMAIL ENVIADO
	private boolean renderEmailProfessor = false; 
	private String  gRecaptchaResponse;
	
	private static final String PAGE_NEW_PROFSSOR            = "/create/novoProfessor";
	private static final String PAGE_EMAIL_SENT              = "/user-tools/emailEnviado";
	private static final String PAGE_LIST_QUESTIONS          = "/list/listaQuestoes";
	private static final String PAGE_LOGIN_PROFSSOR          = "/login/loginProfessor";
	private static final String PAGE_RECOVER_PROF_PASSWORD   = "/user-tools/recuperarSenhaProfessor";
	private static final String WRONG_EMAIL                  = "EMAIL incorreto, tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com";
	private static final String WRONG_PASSWORD               = "SENHA incorreta, tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com";
	private static final String FORGET_CHECK_RECAPTCHA       = "QUASE só falta você dizer que não é um robô!";
	private static final String RECAPTCHA_FAILED             = "CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!";
	private static final String EMAIL_WAS_SENT_WITH_PASSWORD = "ENVIAMOS um email com a sua senha, para o endereço cadastrado. dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String NAME_NOT_FOUND               = "NOME não encontrado (maiúsculas e minúsculas não interferem), dúvidas entre em contato conosco: jadircmj@hotmail.com";
	
	public ControllerProfessores() {}
	
	@PostConstruct
	public void init() {
		this.professor = new Professor();
		this.email = new Email();
	}
	
	// SAVE
	public String save() throws Exception {

		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_NEW_PROFSSOR;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage(RECAPTCHA_FAILED));
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
		if(login.equals("ok"))  
			return PAGE_LIST_QUESTIONS;
		
		// EMAIL ERRADO
		else if(login.equals("email")) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage("messages", new FacesMessage(WRONG_EMAIL));
				return PAGE_LOGIN_PROFSSOR;
			
			// SENHA INCORRETA	
			} else if(login.equals("senha")) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage("messages", new FacesMessage(WRONG_PASSWORD));
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
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws Exception {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_RECOVER_PROF_PASSWORD;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage(RECAPTCHA_FAILED));
			return PAGE_RECOVER_PROF_PASSWORD;
		}	
		
		this.dao = new ProfessorDAO();
		String resultado = this.dao.recuperarSenha(this.professor); 
		
		if(resultado.equals("ok")) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.professor); // ENVIANDO EMAIL SENHA
			ctx.addMessage("messages", new FacesMessage(EMAIL_WAS_SENT_WITH_PASSWORD));
		} else if(resultado.equals("nome")) 
				ctx.addMessage("messages", new FacesMessage(NAME_NOT_FOUND));

		return PAGE_RECOVER_PROF_PASSWORD;		
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

		List<SelectItem> items = new ArrayList<SelectItem>();
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
