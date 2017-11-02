	package br.edu.unicid.web;

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
	// RESPOSTA QUE O USER DEU ON RECAPTCHA
	private String   gRecaptchaResponse;
	// AUX PARA RENDERIZAR MSG DE EMAIL ENVIADO
	private boolean  renderEmailAluno = false;  
	private DataModel<Aluno> listaAluno;
	
	// BEAN CURSOS
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
	public String save() throws Exception {
								
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage("QUASE só falta você dizer que não é um robô!"));
			return "/create/novoAluno";
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage("CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!"));
			return "/create/novoAluno";
		}		
		
		this.date = new Date();
		this.aluno.setData(this.data.format(date)); // SETA DATA EM QUE O ALUNO ESTA SENDO SALVO
		
		this.dao = new AlunoDAO();
		this.aluno.setCodCurso(this.cursosBean.getCurso().getCodigo()); // SET COD CURSO SELECIONADO NO BEAN
		if(this.dao.salvar(this.aluno)) { // IF ALUNO SALVO COM SUCESSO ENTAO ELE RECEBE UM EMAIL
			this.email.sendEmailVerificacao(this.aluno); // PASSANDO O ALUNO PARA Q ELE RECEBA O EMAIL
			this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
			return "/user-tools/emailEnviado";
		}
		return "/create/novoAluno";
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
		String login = this.dao.login(this.aluno);
		if(login.equals("ok")) // LOGIN EFETUADO
			return "/list/listaProvasAluno"; 
		else if(login.equals("rgm")) { // ERRO DE RGM
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage("messages", new FacesMessage("RGM incorreto, tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
			} else if(login.equals("senha")) { // ERRO DE SENHA
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage("messages", new FacesMessage("SENHA incorreta, tente novamente. Caso não consiga se lembrar entre em contato conosco: jadircmj@hotmail.com"));
				} else if(login.equals("unverified")) { // USER NAO VERIFICOU EMAIL
						this.email.sendEmailVerificacao(this.aluno); // ENVIANDO EMAIL NOVAMENTE
						this.renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
						return "/user-tools/emailEnviado";
					}
		return "/login/loginAluno";
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
	public String recuperarSenha() throws Exception {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		this.gRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(this.gRecaptchaResponse.equals("")) { 
			ctx.addMessage("messages", new FacesMessage("QUASE só falta você dizer que não é um robô!"));
			return "/user-tools/recuperarSenhaAluno";
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(this.gRecaptchaResponse)) {
			ctx.addMessage("messages", new FacesMessage("CLIQUE EM: 'não sou um robô', para confirmar que você é uma pessoa!"));
			return "/user-tools/recuperarSenhaAluno";
		}	
		
		this.dao = new AlunoDAO();
		String resultado = this.dao.recuperarSenha(this.aluno); 
		
		if(resultado.equals("ok")) { // ALUNO FOI ENCONTRADO
			this.email.sendEmailSenha(this.aluno); // ENVIANDO EMAIL SENHA
			ctx.addMessage("messages", new FacesMessage("ENVIAMOS um email com a sua senha, para o endereço cadastrado. dúvidas entre em contato conosco: jadircmj@hotmail.com"));
		} else if(resultado.equals("nome")) 
				ctx.addMessage("messages", new FacesMessage("NOME não encontrado (maiúsculas e minúsculas não interferem), dúvidas entre em contato conosco: jadircmj@hotmail.com"));
			else
				ctx.addMessage("messages", new FacesMessage("RGM não coincide com o nome, dúvidas entre em contato conosco: jadircmj@hotmail.com"));

		return "/user-tools/recuperarSenhaAluno";		
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
		return "/login/loginAluno";
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
