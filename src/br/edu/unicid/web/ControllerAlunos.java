	package br.edu.unicid.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.servlet.http.HttpServletRequest;

import br.edu.unicid.bean.Aluno;
import br.edu.unicid.dao.AlunoDAO;
import br.edu.unicid.util.Email;
import br.edu.unicid.util.VerifyRecaptcha;

@ManagedBean(name="controllerAlunos")
@SessionScoped
public class ControllerAlunos {

	private AlunoDAO dao;
	private Aluno    aluno;
	private Email    email;
	private Date     date; // TO KNOW WHEN THE USER IS REGISTERED
	private String   userRecaptchaResponse;
	// AUX PARA RENDERIZAR MSG DE EMAIL ENVIADO
	private boolean  renderEmailAluno = Boolean.FALSE;  
	private DataModel<Aluno> listaAluno;

	private static final String PAGE_NEW_STUDENT          = "/create/novoAluno";
	private static final String PAGE_EMAIL_SENT           = "/user-tools/emailEnviado";
	private static final String PAGE_LOGIN_STUDENT        = "/login/loginAluno";
	private static final String PAGE_RECOVER_STUDENT_PASS = "/user-tools/recuperarSenhaAluno";
	private static final String WRONG_RGM                 = "RGM incorreto.";
	private static final String WRONG_PASS                = "SENHA incorreta.";
	private static final String FORGET_CHECK_RECAPTCHA    = "QUASE só falta você dizer que não é um robô!";
	private static final String RECAPTCHA_FAILED          = "CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!";
	private static final String EMAIL_WAS_SENT_WITH_PASS  = "ENVIAMOS um email com a sua senha, para o endereço cadastrado. dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String NAME_NOT_FOUND            = "NOME não encontrado (maiúsculas e minúsculas não interferem), dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String UNKNOWN_RGM               = "RGM não coincide com o nome, dúvidas entre em contato conosco: jadircmj@hotmail.com";
	private static final String FACE_MESSAGES_ID          = "messages";
	
	private final DateFormat DATA = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
	
	@ManagedProperty(value="#{controllerCursos}")
	private ControllerCursos cursosBean;
	
	// DATEFORMAT PARA SABER A HORA EM QUE O ALUNO FOI SALVO

	public ControllerAlunos() {}
		
	@PostConstruct
	public void init() {
		this.aluno = new Aluno();
		this.email = new Email();
	}
		
	// SAVE
	public String save() throws IOException {
								
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.userRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_LOGIN_STUDENT;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(RECAPTCHA_FAILED));
			return PAGE_LOGIN_STUDENT;
		}		
		
		this.date = new Date();
		this.aluno.setData(DATA.format(date)); // SETA DATA EM QUE O ALUNO ESTA SENDO SALVO
		
		this.dao = new AlunoDAO();
		this.aluno.setCodCurso(this.cursosBean.getCurso().getCodigo()); // SET COD CURSO SELECIONADO NO BEAN
		if(this.dao.salvar(this.aluno)) { // IF ALUNO SALVO COM SUCESSO ENTAO ELE RECEBE UM EMAIL
			this.email.sendEmailVerificacao(this.aluno); // PASSANDO O ALUNO PARA Q ELE RECEBA O EMAIL
			this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
			return PAGE_EMAIL_SENT;
		}
		return PAGE_NEW_STUDENT;
	}
		
	// ALTERAR
	public String alterar() {
		this.dao = new AlunoDAO();
		return (this.dao.alterar(this.aluno)) ? "homeAluno" : "/update/alterarAluno";
	}
	
	// EXCLUIR
	public void excluir() {
		dao = new AlunoDAO();
		if(dao.excluir(aluno.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage("Aluno excluido!"));				
		}
	}
	
	// LOGIN
	public String login() {
		this.dao = new AlunoDAO();
		String loginAttemptStatus = this.dao.login(this.aluno);
		
		if(loginAttemptStatus.equals("ok")) 
			return "/list/listaProvasAluno";
		
		else if(loginAttemptStatus.equals("rgm")) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(WRONG_RGM));
			
			} else if(loginAttemptStatus.equals("senha")) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(WRONG_PASS));
				
				} else if(loginAttemptStatus.equals("unverified")) {
						this.email.sendEmailVerificacao(this.aluno); // ENVIANDO EMAIL NOVAMENTE
						this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
						return PAGE_EMAIL_SENT;
					}
		return PAGE_LOGIN_STUDENT;
	}
	
	// AUTENTICA 
	public String autenticaAluno(String info) {
		// OBTEM OS DADOS VINDOS DA URL
		String[] infos = info.split(" ");

		// SETA OS DADOS NO BEAN
		this.aluno.setData(infos[0]);
		this.aluno.setEmail(infos[1]);

		this.dao = new AlunoDAO();
		
		// VERIFICA A AUTENTICIDADE DESSES DADOS
		if(this.dao.autentica(this.aluno))
			return "Pronto!";
		
		return "Algo não aconteceu como o esperado!";		
	}
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(userRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(FORGET_CHECK_RECAPTCHA));
			return PAGE_RECOVER_STUDENT_PASS;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(RECAPTCHA_FAILED));
			return PAGE_RECOVER_STUDENT_PASS;
		}	
		
		this.dao = new AlunoDAO();
		String resultado = this.dao.recuperarSenha(this.aluno); 
		
		if(resultado.equals("ok")) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.aluno); // ENVIANDO EMAIL SENHA
			ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(EMAIL_WAS_SENT_WITH_PASS));
		} else if(resultado.equals("nome")) 
				ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(NAME_NOT_FOUND));
			else
				ctx.addMessage(FACE_MESSAGES_ID, new FacesMessage(UNKNOWN_RGM));

		return PAGE_RECOVER_STUDENT_PASS;		
	}
	
	// OBTER NOME ALUNO
	public String getNome(int codAluno) {
		this.dao = new AlunoDAO();
		// RETORNA O NOME, CASO NAO SEJA ECONTRADO RETORNA 'NAO FOI ENCONTRADO'
		return dao.getNome(codAluno);  
	}

	// OBTER RGM ALUNO
	public int getRgm(int codAluno) {
		dao = new AlunoDAO();
		// RETORNA O RGM, CASO NAO SEJA ENCONTRADO RETORNA 0
		return dao.getRgm(codAluno);  
	}
	
	// RENDERIZADOR DE ICONE DE EMAIL
	public boolean renderEmail(String email) {
		return (aluno.getEmail().contains(email)) ? true : false;
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.aluno = listaAluno.getRowData();
	}
	
	// LOG OFF
	public String quit() {
		init();
		return PAGE_LOGIN_STUDENT;
	}
	
	// GETTERS AND SETTERS
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public ControllerCursos getCursosBean() {
		return cursosBean;
	}
	public void setCursosBean(ControllerCursos cursosBean) {
		this.cursosBean = cursosBean;
	}
	public boolean isRenderEmailAluno() {
		return renderEmailAluno;
	}
	public void setRenderEmailAluno(boolean renderEmailAluno) {
		this.renderEmailAluno = renderEmailAluno;
	}
}
