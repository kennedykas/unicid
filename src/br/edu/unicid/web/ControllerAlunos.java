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
	private boolean  renderEmailAluno = false;  
	private DataModel<Aluno> listaAluno;

	private static final String PAGINA_NOVO_ALUNO            = "/create/novoAluno";
	private static final String PAGINA_EMAIL_ENVIADO         = "/user-tools/emailEnviado";
	private static final String PAGINA_LOGIN_ALUNO           = "/login/loginAluno";
	private static final String PAGINA_RECUPERAR_SENHA_ALUNO = "/user-tools/recuperarSenhaAluno";
	
	@ManagedProperty(value="#{controllerCursos}")
	private ControllerCursos cursosBean;
	
	// DATEFORMAT PARA SABER A HORA EM QUE O ALUNO FOI SALVO
	private DateFormat data = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");

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
		if(this.userRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage("QUASE s� falta voc� dizer que n�o � um rob�!"));
			return PAGINA_NOVO_ALUNO;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage("CLIQUE EM: 'n�o sou um rob�', para confirmar que voc� � uma pessoa!"));
			return PAGINA_NOVO_ALUNO;
		}		
		
		this.date = new Date();
		this.aluno.setData(this.data.format(date)); // SETA DATA EM QUE O ALUNO ESTA SENDO SALVO
		
		this.dao = new AlunoDAO();
		this.aluno.setCodCurso(this.cursosBean.getCurso().getCodigo()); // SET COD CURSO SELECIONADO NO BEAN
		if(this.dao.salvar(this.aluno)) { // IF ALUNO SALVO COM SUCESSO ENTAO ELE RECEBE UM EMAIL
			this.email.sendEmailVerificacao(this.aluno); // PASSANDO O ALUNO PARA Q ELE RECEBA O EMAIL
			this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
			return PAGINA_EMAIL_ENVIADO;
		}
		return PAGINA_NOVO_ALUNO;
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
			ctx.addMessage("messages", new FacesMessage("Aluno excluido!"));				
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
				ctx.addMessage("messages", new FacesMessage("RGM incorreto, tente novamente. Caso n�o consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
			
			} else if(loginAttemptStatus.equals("senha")) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage("messages", new FacesMessage("SENHA incorreta, tente novamente. Caso n�o consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
				
				} else if(loginAttemptStatus.equals("unverified")) {
						this.email.sendEmailVerificacao(this.aluno); // ENVIANDO EMAIL NOVAMENTE
						this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
						return PAGINA_EMAIL_ENVIADO;
					}
		return PAGINA_LOGIN_ALUNO;
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
		
		return "Algo n�o aconteceu como o esperado!";		
	}
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(userRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage("QUASE s� falta voc� dizer que n�o � um rob�!"));
			return PAGINA_RECUPERAR_SENHA_ALUNO;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage("CLIQUE EM: 'n�o sou um rob�', para confirmar que voc� � uma pessoa!"));
			return PAGINA_RECUPERAR_SENHA_ALUNO;
		}	
		
		this.dao = new AlunoDAO();
		String resultado = this.dao.recuperarSenha(this.aluno); 
		
		if(resultado.equals("ok")) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.aluno); // ENVIANDO EMAIL SENHA
			ctx.addMessage("messages", new FacesMessage("ENVIAMOS um email com a sua senha, para o endere�o cadastrado. d�vidas entre em contato conosco: jadircmj@hotmail.com"));
		} else if(resultado.equals("nome")) 
				ctx.addMessage("messages", new FacesMessage("NOME n�o encontrado (mai�sculas e min�sculas n�o interferem), d�vidas entre em contato conosco: jadircmj@hotmail.com"));
			else
				ctx.addMessage("messages", new FacesMessage("RGM n�o coincide com o nome, d�vidas entre em contato conosco: jadircmj@hotmail.com"));

		return PAGINA_RECUPERAR_SENHA_ALUNO;		
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
		return PAGINA_LOGIN_ALUNO;
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
