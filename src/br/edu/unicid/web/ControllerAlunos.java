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
import br.edu.unicid.constants.Constants;
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
		userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(userRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.FORGET_CHECK_RECAPTCHA));
			return Constants.PAGE_LOGIN_STUDENT;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.RECAPTCHA_FAILED));
			return Constants.PAGE_LOGIN_STUDENT;
		}		
		
		date = new Date();
		aluno.setData(DATA.format(date));
		
		dao = new AlunoDAO();
		aluno.setCodCurso(cursosBean.getCurso().getCodigo());
		
		if (dao.salvar(aluno)) {
			email.sendEmailVerificacao(aluno);
			renderEmailAluno = true;
			return Constants.PAGE_EMAIL_SENT;
		}
		return Constants.PAGE_NEW_STUDENT;
	}
		
	// ALTERAR
	public String updateStudent() {
		
		dao = new AlunoDAO();

		aluno.setCodCurso(cursosBean.getCurso().getCodigo()); // SET COD CURSO SELECIONADO NO BEAN
		
		return (dao.updateStudent(aluno)) ? Constants.PAGE_LIST_TESTS_STUDENT : Constants.PAGE_UPDATE_STUDENT_INFO;
	}
	
	// EXCLUIR
	public void excluir() {
		dao = new AlunoDAO();
		if(dao.excluir(aluno.getCodigo())) { 
			FacesContext ctx = FacesContext.getCurrentInstance();
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage("Aluno excluido!"));				
		}
	}
	
	// LOGIN
	public String login() {
		
		dao = new AlunoDAO();
		String loginAttemptStatus = dao.login(aluno);
		
		if(loginAttemptStatus.equals(Constants.SUCCESS)) 
			return Constants.PAGE_LIST_TESTS_STUDENT;
		
		else if(loginAttemptStatus.equals(Constants.RGM)) { 
				FacesContext ctx = FacesContext.getCurrentInstance();
				ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.WRONG_RGM));
			
			} else if(loginAttemptStatus.equals(Constants.RGM)) { 
					FacesContext ctx = FacesContext.getCurrentInstance();
					ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.WRONG_PASS));
				
				// ACCOUNT UNVERIFIED SENDING EMAIL AGAIN
				} else if(loginAttemptStatus.equals(Constants.UNVERIFIED)) {
						email.sendEmailVerificacao(aluno);
						renderEmailAluno = true; // MSG SERA RENDERIZADA PARA O ALUNO
						return Constants.PAGE_EMAIL_SENT;
					}
		return Constants.PAGE_LOGIN_STUDENT;
	}
	
	// AUTENTICA 
	public String autenticaAluno(String info) {
		// OBTEM OS DADOS VINDOS DA URL
		String[] infos = info.split(" ");

		// SETA OS DADOS NO BEAN
		aluno.setData(infos[0]);
		aluno.setEmail(infos[1]);

		dao = new AlunoDAO();
		
		// VERIFICA A AUTENTICIDADE DESSES DADOS
		if(dao.autentica(aluno))
			return Constants.READY;
		
		return Constants.SOMETHING_WENT_WRONG;		
	}
	
	// RECUPERAR SENHA 
	public String recuperarSenha() throws IOException {
		
		HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		FacesContext ctx = FacesContext.getCurrentInstance();
		
		// RESPOSTA DA DIV DO RECAPTCHA
		userRecaptchaResponse = req.getParameter("g-recaptcha-response");

		// RECAPTCHA NAO CHECADO
		if(userRecaptchaResponse.isEmpty()) { 
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.FORGET_CHECK_RECAPTCHA));
			return Constants.PAGE_RECOVER_STUDENT_PASS;
		}
		// RECAPTCHA ROBO (false = robot / true = people)
		else if(!VerifyRecaptcha.verify(userRecaptchaResponse)) {
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.RECAPTCHA_FAILED));
			return Constants.PAGE_RECOVER_STUDENT_PASS;
		}	
		
		dao = new AlunoDAO();
		String resultado = dao.recuperarSenha(aluno); 
		
		if(resultado.equals(Constants.SUCCESS)) { // ALUNO FOI ENCONTRADO
			email.sendEmailSenha(aluno); // ENVIANDO EMAIL SENHA
			ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.EMAIL_WAS_SENT_WITH_PASS));
		} else if(resultado.equals(Constants.NAME)) 
				ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.NAME_NOT_FOUND));
			else
				ctx.addMessage(Constants.FACE_MESSAGES_ID, new FacesMessage(Constants.UNKNOWN_RGM));

		return Constants.PAGE_RECOVER_STUDENT_PASS;		
	}
	
	public String getNome(int codAluno) {
	
		return new AlunoDAO().getNome(codAluno);  
	}
	
	public void getNameByRgm() {
		
		if (aluno.getRgm() != 0)
			aluno.setNome(new AlunoDAO().getNameByRgm(aluno.getRgm()));
		
		//FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("deposit-form:name");
	}
	
	public String getNameByRgm(int rgm) {
		return new AlunoDAO().getNameByRgm(rgm);
	}

	public int getRgm(int codAluno) {
		dao = new AlunoDAO();
		// RETORNA O RGM, CASO NAO SEJA ENCONTRADO RETORNA 0
		return dao.getRgm(codAluno);  
	}
	
	// RENDERIZADOR DE ICONE DE EMAIL
	public boolean renderEmail(String email) {
		return (aluno.getEmail().contains(email)) ? Boolean.TRUE : Boolean.FALSE;
	}
	
	// GET ROW DATA
	public void selecionarRegistro() {
		this.aluno = listaAluno.getRowData();
	}
	
	public String quit() {
		init();
		return Constants.PAGE_LOGIN_STUDENT;
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
